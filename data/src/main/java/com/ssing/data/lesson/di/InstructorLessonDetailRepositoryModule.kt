package com.ssing.data.lesson.di

import com.ssing.data.lesson.repository.api.InstructorLessonDetailRepository
import com.ssing.data.lesson.repository.impl.InstructorLessonDetailRepositoryImpl
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