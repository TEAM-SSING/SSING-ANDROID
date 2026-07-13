package com.ssing.data.lesson.di

import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.datasource.impl.LessonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLessonDataSource(
        impl: LessonDataSourceImpl,
    ): LessonDataSource
}