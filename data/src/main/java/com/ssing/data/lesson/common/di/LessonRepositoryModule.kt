package com.ssing.data.lesson.common.di

import com.ssing.data.lesson.common.repository.api.LessonRepository
import com.ssing.data.lesson.common.repository.impl.LessonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonRepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindLessonRepository(
        impl: LessonRepositoryImpl,
    ): LessonRepository
}