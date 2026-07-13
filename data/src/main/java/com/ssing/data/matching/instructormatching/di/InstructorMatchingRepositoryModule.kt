package com.ssing.data.instructormatching.di

import com.ssing.data.instructormatching.repository.api.InstructorMatchingRepository
import com.ssing.data.instructormatching.repository.impl.InstructorMatchingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorMatchingRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindInstructorMatchingRepository(
        impl: InstructorMatchingRepositoryImpl,
    ): InstructorMatchingRepository
}
