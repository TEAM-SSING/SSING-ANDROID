package com.ssing.data.instructorlessondetail.di

import com.ssing.data.instructorlessondetail.remote.service.InstructorLessonDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InstructorLessonDetailServiceModule {

    @Provides
    @Singleton
    fun instructorLessonDetailService(
        retrofit: Retrofit
    ): InstructorLessonDetailService =
        retrofit.create(InstructorLessonDetailService::class.java)
}