package com.ssing.data.matching.instructormatching.di

import com.ssing.core.network.di.Auth
import com.ssing.data.matching.instructormatching.remote.service.InstructorMatchingService
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
        @Auth retrofit: Retrofit,
    ): InstructorMatchingService = retrofit.create(InstructorMatchingService::class.java)
}
