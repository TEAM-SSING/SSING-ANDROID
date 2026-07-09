package com.ssing.data.auth.di

import com.ssing.data.auth.remote.datasource.api.LogoutDataSource
import com.ssing.data.auth.remote.datasource.impl.LogoutDataSourceImpl
import com.ssing.data.auth.remote.service.LogoutService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LogoutDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLogoutDataSource(
        impl: LogoutDataSourceImpl,
    ): LogoutDataSource

    companion object {
        @Provides
        @Singleton
        fun provideLogoutService(retrofit: Retrofit): LogoutService =
            retrofit.create(LogoutService::class.java)
    }
}
