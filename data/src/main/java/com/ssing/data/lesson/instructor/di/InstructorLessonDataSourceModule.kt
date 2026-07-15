package com.ssing.data.lesson.instructor.di

import com.ssing.data.lesson.instructor.remote.datasource.api.InstructorLessonDataSource
import com.ssing.data.lesson.instructor.remote.datasource.impl.InstructorLessonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorLessonDataSourceModule {
    @Binds
    @Singleton
    internal abstract fun bindInstructorLessonDataSource(
        impl: InstructorLessonDataSourceImpl,
    ): InstructorLessonDataSource
}