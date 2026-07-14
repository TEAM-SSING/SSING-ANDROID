package com.ssing.data.devauth.remote.di

import com.ssing.core.network.di.NoAuth
import com.ssing.data.devauth.remote.service.DevAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DevAuthServiceModule {
    @Provides
    @Singleton
    fun provideDevAuthService(@NoAuth retrofit: Retrofit): DevAuthService =
        retrofit.create(DevAuthService::class.java)
}
