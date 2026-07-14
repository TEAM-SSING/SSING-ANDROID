package com.ssing.data.lesson.instructorlesson.di

import com.ssing.core.network.di.Auth
import com.ssing.data.lesson.instructorlesson.repository.api.InstructorLessonRepository
import com.ssing.data.lesson.repository.impl.InstructorLessonRepositoryImpl
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
        impl: InstructorLessonRepositoryImpl,
    ): InstructorLessonRepository
}
