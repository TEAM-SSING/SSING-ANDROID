package com.ssing.data.lesson.consumer.di

import com.ssing.data.lesson.consumer.remote.datasource.api.ConsumerLessonDataSource
import com.ssing.data.lesson.consumer.remote.datasource.impl.ConsumerLessonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerLessonDataSourceModule {
    @Binds
    @Singleton
    internal abstract fun bindConsumerLessonDataSource(
        impl: ConsumerLessonDataSourceImpl,
    ): ConsumerLessonDataSource
}