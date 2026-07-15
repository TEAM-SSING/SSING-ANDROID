package com.ssing.data.lesson.di

import com.ssing.data.lesson.repository.api.LessonSocketRepository
import com.ssing.data.lesson.repository.impl.LessonSocketRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonSocketRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindLessonSocketRepository(
        impl: LessonSocketRepositoryImpl,
    ): LessonSocketRepository
}