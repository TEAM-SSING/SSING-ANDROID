package com.ssing.core.notification.data.di

import android.content.Context
import com.ssing.core.notification.ClientApp
import com.ssing.core.notification.data.remote.datasource.FcmTokenDataSource
import com.ssing.core.notification.data.repository.FcmTokenRepository
import com.ssing.core.notification.data.repository.api.FcmTokenService
import com.ssing.core.notification.data.repository.impl.FcmTokenDataSourceImpl
import com.ssing.core.notification.data.repository.impl.FcmTokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.ssing.core.network.di.Auth
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FcmTokenModule {

    @Binds
    @Singleton
    abstract fun bindNotificationDataSource(
        impl: FcmTokenDataSourceImpl
    ): FcmTokenDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: FcmTokenRepositoryImpl
    ): FcmTokenRepository

    companion object {
        @Provides
        @Singleton
        fun provideNotificationApi(@Auth retrofit: Retrofit): FcmTokenService =
            retrofit.create(FcmTokenService::class.java)

        @Provides
        @Singleton
        fun provideClientApp(@ApplicationContext context: Context): ClientApp =
            if (context.packageName.contains("instructor", ignoreCase = true)) {
                ClientApp.INSTRUCTOR
            } else {
                ClientApp.CONSUMER
            }
    }
}
