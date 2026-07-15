package com.ssing.data.consumerlesson.di

import com.ssing.data.consumerlesson.remote.datasource.api.ConsumerLessonDetailDataSource
import com.ssing.data.consumerlesson.remote.datasource.impl.ConsumerLessonDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConsumerLessonDetailDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindConsumerLessonDetailDataSource(
        impl: ConsumerLessonDetailDataSourceImpl): ConsumerLessonDetailDataSource
}