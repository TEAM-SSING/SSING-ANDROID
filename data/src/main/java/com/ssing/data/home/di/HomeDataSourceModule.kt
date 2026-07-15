package com.ssing.data.home.di

import com.ssing.data.home.remote.datasource.api.HomeRemoteDataSource
import com.ssing.data.home.remote.datasource.impl.HomeRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindConsumerHomeDataSource(
        impl: HomeRemoteDataSourceImpl,
    ): HomeRemoteDataSource
}
