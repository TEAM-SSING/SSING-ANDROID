package com.ssing.data.consumerlesson.di

import com.ssing.data.consumerlesson.repository.api.ConsumerLessonDetailRepository
import com.ssing.data.consumerlesson.repository.impl.ConsumerLessonDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerLessonDetailRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindConsumerLessonDetailRepository(
        impl: ConsumerLessonDetailRepositoryImpl
    ): ConsumerLessonDetailRepository
}