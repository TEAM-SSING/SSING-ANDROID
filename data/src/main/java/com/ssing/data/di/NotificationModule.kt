package com.ssing.data.di

import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.repository.NotificationRepository
import com.ssing.data.repository.impl.NotificationDataSourceImpl
import com.ssing.data.repository.impl.NotificationRepositoryImpl
import com.ssing.data.repository.api.NotificationApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

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
        fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
            retrofit.create(NotificationApi::class.java)
    }
}

