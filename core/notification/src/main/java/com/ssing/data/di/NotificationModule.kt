package com.ssing.data.di

import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.repository.NotificationRepository
import com.ssing.data.repository.api.NotificationService
import com.ssing.data.repository.impl.NotificationDataSourceImpl
import com.ssing.data.repository.impl.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.ssing.core.network.di.Auth
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindNotificationDataSource(
        impl: NotificationDataSourceImpl
    ): NotificationDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    companion object {
        @Provides
        @Singleton
        fun provideNotificationApi(@Auth retrofit: Retrofit): NotificationService =
            retrofit.create(NotificationService::class.java)
    }
}