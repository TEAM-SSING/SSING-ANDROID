package com.ssing.data.lessoncancel.di

import com.ssing.data.lessoncancel.remote.service.LessonCancelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LessonCancelServiceModule {
    @Provides
    @Singleton
    fun provideLessonCancelService(
        retrofit: Retrofit,
    ): LessonCancelService = retrofit.create(LessonCancelService::class.java)
}