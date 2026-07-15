package com.ssing.data.lesson.instructor.di

import com.ssing.core.network.di.Auth
import com.ssing.data.lesson.instructor.remote.service.InstructorLessonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InstructorLessonServiceModule {
    @Provides
    @Singleton
    internal fun provideInstructorLessonService(
        @Auth retrofit: Retrofit
    ): InstructorLessonService =
        retrofit.create(InstructorLessonService::class.java)
}