package com.ssing.data.lesson.consumer.di

import com.ssing.core.network.di.Auth
import com.ssing.data.lesson.consumer.remote.service.ConsumerLessonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConsumerLessonServiceModule {
    @Provides
    @Singleton
    internal fun provideConsumerLessonService(
        @Auth retrofit: Retrofit
    ): ConsumerLessonService =
        retrofit.create(ConsumerLessonService::class.java)
}