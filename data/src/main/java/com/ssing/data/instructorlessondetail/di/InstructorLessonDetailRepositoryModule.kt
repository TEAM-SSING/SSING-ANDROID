package com.ssing.data.instructorlessondetail.di

import com.ssing.data.instructorlessondetail.repository.api.InstructorLessonDetailRepository
import com.ssing.data.instructorlessondetail.repository.impl.InstructorLessonDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorLessonDetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindInstructorLessonDetailRepository(
        impl: InstructorLessonDetailRepositoryImpl,
    ): InstructorLessonDetailRepository
}