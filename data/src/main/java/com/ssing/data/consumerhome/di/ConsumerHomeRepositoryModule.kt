package com.ssing.data.consumerhome.di

import com.ssing.data.consumerhome.remote.datasource.api.ConsumerHomeRemoteDataSource
import com.ssing.data.consumerhome.repository.impl.ConsumerHomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerHomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConsumerRepositorySource(
        impl: ConsumerHomeRepositoryImpl,
    ): ConsumerHomeRemoteDataSource
}