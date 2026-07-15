package com.ssing.data.lesson.instructor.di

import com.ssing.data.lesson.instructor.repository.api.InstructorLessonRepository
import com.ssing.data.lesson.instructor.repository.impl.InstructorLessonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorLessonRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindInstructorLessonRepository(
        impl: InstructorLessonRepositoryImpl
    ): InstructorLessonRepository
}
