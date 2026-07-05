package com.ssing.data.consumerlogin.di

import com.ssing.data.consumerlogin.repository.api.ConsumerLoginRepository
import com.ssing.data.consumerlogin.repository.impl.ConsumerLoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerLoginRepositoryModule {

    @Binds
    abstract fun bindConsumerLoginRepository(
        impl: ConsumerLoginRepositoryImpl,
    ): ConsumerLoginRepository
}