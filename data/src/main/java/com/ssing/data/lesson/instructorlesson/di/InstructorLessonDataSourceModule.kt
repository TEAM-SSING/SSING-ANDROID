package com.ssing.data.lesson.instructorlesson.di

import com.google.android.gms.auth.api.Auth
import com.ssing.core.network.di.Auth
import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.datasource.impl.LessonDataSourceImpl
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
    abstract fun bindInstructorLessonDataSource(
        @Auth impl: LessonDataSourceImpl,
    ): LessonDataSource
}
