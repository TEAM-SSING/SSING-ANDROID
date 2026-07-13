package com.ssing.data.lesson.di

import com.ssing.data.lesson.repository.api.StartConfirmationRepository
import com.ssing.data.lesson.repository.impl.StartConfirmationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StartConfirmationRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindStartConfirmationRepository(
        impl: StartConfirmationRepositoryImpl,
    ): StartConfirmationRepository
}