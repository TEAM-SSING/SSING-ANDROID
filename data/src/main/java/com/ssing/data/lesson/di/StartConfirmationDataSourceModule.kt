package com.ssing.data.lesson.di

import com.ssing.data.lesson.remote.datasource.api.StartConfirmationDataSource
import com.ssing.data.lesson.remote.datasource.impl.StartConfirmationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StartConfirmationDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindStartConfirmationDataSource(
        impl: StartConfirmationDataSourceImpl,
    ): StartConfirmationDataSource
}