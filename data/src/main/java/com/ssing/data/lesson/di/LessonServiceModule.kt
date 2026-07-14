package com.ssing.data.lesson.di

import com.ssing.core.network.di.Auth
import com.ssing.data.lesson.remote.service.LessonService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LessonServiceModule {

    @Provides
    @Singleton
    fun provideLessonService(
        @Auth retrofit: Retrofit
    ): LessonService = retrofit.create(LessonService::class.java)
}