package com.ssing.data.auth.di

import com.ssing.data.auth.repository.api.LogoutRepository
import com.ssing.data.auth.repository.impl.LogoutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LogoutRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLogoutRepository(
        impl: LogoutRepositoryImpl,
    ): LogoutRepository
}
