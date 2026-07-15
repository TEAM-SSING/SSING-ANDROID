package com.ssing.data.lessoncancel.di

import com.ssing.data.lessoncancel.remote.datasource.api.LessonCancelDataSource
import com.ssing.data.lessoncancel.remote.datasource.impl.LessonCancelDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonCancelDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLessonCancelDataSource(
        impl: LessonCancelDataSourceImpl,
    ): LessonCancelDataSource
}