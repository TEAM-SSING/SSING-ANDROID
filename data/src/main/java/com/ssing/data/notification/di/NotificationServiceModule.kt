package com.ssing.data.notification.di

import com.ssing.core.network.di.Auth
import com.ssing.data.notification.remote.service.NotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NotificationServiceModule {

    @Provides
    @Singleton
    fun provideNotificationService(
        @Auth retrofit: Retrofit,
    ): NotificationService = retrofit.create(NotificationService::class.java)
}
