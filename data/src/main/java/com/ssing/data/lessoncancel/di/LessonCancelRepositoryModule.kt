package com.ssing.data.lessoncancel.di

import com.ssing.data.lessoncancel.repository.api.LessonCancelRepository
import com.ssing.data.lessoncancel.repository.impl.LessonCancelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonCancelRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLessonCancelRepository(
        impl: LessonCancelRepositoryImpl,
    ): LessonCancelRepository
}