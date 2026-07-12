package com.ssing.data.consumermatching.di

import com.ssing.data.consumermatching.remote.datasource.api.ConsumerMatchingRemoteDataSource
import com.ssing.data.consumermatching.remote.datasource.impl.ConsumerMatchingRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerMatchingDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindConsumerMatchingRemoteDataSource(
        impl: ConsumerMatchingRemoteDataSourceImpl,
    ): ConsumerMatchingRemoteDataSource
}
