package com.ssing.data.instructormatching.di

import com.ssing.data.instructormatching.remote.service.InstructorMatchingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InstructorMatchingServiceModule {

    @Provides
    @Singleton
    fun provideInstructorMatchingService(
        retrofit: Retrofit,
    ): InstructorMatchingService = retrofit.create(InstructorMatchingService::class.java)
}
