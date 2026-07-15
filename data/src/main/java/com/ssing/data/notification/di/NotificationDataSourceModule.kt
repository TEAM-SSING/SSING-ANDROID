package com.ssing.data.notification.di

import com.ssing.data.notification.remote.datasource.api.NotificationRemoteDataSource
import com.ssing.data.notification.remote.datasource.impl.NotificationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNotificationRemoteDataSource(
        impl: NotificationRemoteDataSourceImpl,
    ): NotificationRemoteDataSource
}
