package com.ssing.data.home.di

import com.ssing.core.network.di.Auth
import com.ssing.data.home.remote.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class HomeServiceModule {

    @Provides
    @Singleton
    fun provideConsumerService(
        @Auth retrofit: Retrofit,
    ): HomeService = retrofit.create(HomeService::class.java)
}
