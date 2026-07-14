package com.ssing.data.lesson.common.di

import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import com.ssing.data.lesson.common.remote.datasource.impl.LessonSocketDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonSocketDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLessonSocketDataSource(
        impl: LessonSocketDataSourceImpl,
    ): LessonSocketDataSource
}
