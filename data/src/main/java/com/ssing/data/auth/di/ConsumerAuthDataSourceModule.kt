package com.ssing.data.auth.di

import com.ssing.data.auth.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.auth.remote.datasource.impl.ConsumerAuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConsumerAuthDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindConsumerAuthDataSource(impl: ConsumerAuthDataSourceImpl): ConsumerAuthDataSource
}