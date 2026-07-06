package com.ssing.data.consumerlogin.di

import com.ssing.data.consumerlogin.remote.service.ConsumerAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConsumerAuthDataServiceModule {

    @Provides
    @Singleton
    fun provideConsumerAuthService(
        retrofit: Retrofit
    ): ConsumerAuthService = retrofit.create(ConsumerAuthService::class.java)
}
