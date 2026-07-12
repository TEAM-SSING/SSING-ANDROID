package com.ssing.data.lesson.di

import com.ssing.data.lesson.remote.service.StartConfirmationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StartConfirmationServiceModule {

    @Provides
    @Singleton
    fun provideStartConfirmationService(
        retrofit: Retrofit
    ): StartConfirmationService = retrofit.create(StartConfirmationService::class.java)
}