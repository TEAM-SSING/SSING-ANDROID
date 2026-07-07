package com.ssing.core.network.di

import com.ssing.core.network.BuildConfig
import com.ssing.core.network.authenticator.TokenAuthenticator
import com.ssing.core.network.interceptor.AuthInterceptor
import com.ssing.core.network.service.ReissueService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private fun String.isJsonObject(): Boolean = trimStart().startsWith("{")
private fun String.isJsonArray(): Boolean = trimStart().startsWith("[")

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private const val LOGGING_TAG = "okhttp"
    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(CONTENT_TYPE.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    runCatching { JSONObject(message).toString(4) }
                        .onSuccess { Timber.tag(LOGGING_TAG).d(it) }
                        .onFailure { Timber.tag(LOGGING_TAG).d(message) }

                message.isJsonArray() ->
                    runCatching { JSONArray(message).toString(4) }
                        .onSuccess { Timber.tag(LOGGING_TAG).d(it) }
                        .onFailure { Timber.tag(LOGGING_TAG).d(message) }

                else -> {
                    Timber.tag(LOGGING_TAG).d("CONNECTION INFO -> $message")
                }
            }
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @WithoutTokenOkHttpClient
    @Provides
    @Singleton
    fun provideWithoutTokenOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @WithTokenOkHttpClient
    @Provides
    @Singleton
    internal fun provideWithTokenOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @WithoutTokenOkHttpClient client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    internal fun provideReissueService(retrofit: Retrofit): ReissueService =
        retrofit.create(ReissueService::class.java)

    @SocketOkHttpClient
    @Provides
    @Singleton
    fun provideSocketOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .pingInterval(30, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providerStompClient(@SocketOkHttpClient okHttpClient: OkHttpClient): StompClient =
        StompClient(OkHttpWebSocketClient(okHttpClient))
}

