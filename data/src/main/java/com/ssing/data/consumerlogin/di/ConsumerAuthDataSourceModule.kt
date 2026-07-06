package com.ssing.data.consumerlogin.di

import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerAuthDataSource
import com.ssing.data.consumerlogin.remote.datasource.impl.ConsumerAuthDataSourceImpl
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
