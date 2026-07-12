package com.ssing.data.consumermatching.di

import com.ssing.data.consumermatching.repository.api.ConsumerMatchingRepository
import com.ssing.data.consumermatching.repository.impl.ConsumerMatchingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerMatchingRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConsumerMatchingRepository(
        impl: ConsumerMatchingRepositoryImpl,
    ): ConsumerMatchingRepository
}
