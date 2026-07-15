package com.ssing.data.matching.consumermatching.di

import com.ssing.core.network.di.Auth
import com.ssing.data.matching.consumermatching.remote.service.ConsumerMatchingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConsumerMatchingServiceModule {

    @Provides
    @Singleton
    fun provideConsumerMatchingService(
        @Auth retrofit: Retrofit,
    ): ConsumerMatchingService = retrofit.create(ConsumerMatchingService::class.java)
}
