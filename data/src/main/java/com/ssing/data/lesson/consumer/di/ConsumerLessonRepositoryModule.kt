package com.ssing.data.lesson.consumer.di

import com.ssing.data.lesson.consumer.repository.api.ConsumerLessonRepository
import com.ssing.data.lesson.consumer.repository.impl.ConsumerLessonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerLessonRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindConsumerLessonRepository(
        impl: ConsumerLessonRepositoryImpl
    ): ConsumerLessonRepository
}