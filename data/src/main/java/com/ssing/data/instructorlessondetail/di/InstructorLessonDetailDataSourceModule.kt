package com.ssing.data.instructorlessondetail.di

import com.ssing.data.instructorlessondetail.remote.datasource.api.InstructorLessonDetailDataSource
import com.ssing.data.instructorlessondetail.remote.datasource.impl.InstructorLessonDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorLessonDetailDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindInstructorLessonDetailDataSource(
        impl: InstructorLessonDetailDataSourceImpl,
    ): InstructorLessonDetailDataSource
}