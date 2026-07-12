package com.ssing.data.consumermatching.di

import com.ssing.data.consumermatching.remote.service.ConsumerMatchingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConsumerMatchingServiceModule {

    @Provides
    @Singleton
    fun provideConsumerMatchingService(
        retrofit: Retrofit,
    ): ConsumerMatchingService = retrofit.create(ConsumerMatchingService::class.java)
}
