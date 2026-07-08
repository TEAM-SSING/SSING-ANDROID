package com.ssing.data.auth.di

import com.ssing.data.auth.repository.api.ConsumerAuthRepository
import com.ssing.data.auth.repository.impl.ConsumerAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerAuthRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindConsumerAuthRepository(
        impl: ConsumerAuthRepositoryImpl,
    ): ConsumerAuthRepository
}