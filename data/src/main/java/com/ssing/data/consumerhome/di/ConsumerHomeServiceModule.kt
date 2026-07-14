package com.ssing.data.consumerhome.di

import com.ssing.data.consumerhome.remote.datasource.api.ConsumerHomeRemoteDataSource
import com.ssing.data.consumerhome.remote.service.ConsumerHomeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerHomeServiceModule {

    @Provides
    @Singleton
    fun provideConsumerService(
        retrofit: Retrofit,
    ): ConsumerHomeService = retrofit.create(ConsumerHomeService::class.java)
}