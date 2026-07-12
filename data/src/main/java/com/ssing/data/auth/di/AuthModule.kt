package com.ssing.data.auth.di

import com.ssing.data.auth.remote.datasource.api.AuthDataSource
import com.ssing.data.auth.remote.datasource.impl.AuthDataSourceImpl
import com.ssing.data.auth.remote.service.AuthService
import com.ssing.data.auth.repository.api.AuthRepository
import com.ssing.data.auth.repository.impl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(impl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideAuthService(retrofit: Retrofit): AuthService =
            retrofit.create(AuthService::class.java)
    }
}