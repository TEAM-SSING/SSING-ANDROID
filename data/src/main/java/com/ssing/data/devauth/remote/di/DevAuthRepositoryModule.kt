package com.ssing.data.devauth.remote.di

import com.ssing.data.devauth.repository.api.DevAuthRepository
import com.ssing.data.devauth.repository.impl.DevAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DevAuthRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDevAuthRepository(
        impl: DevAuthRepositoryImpl,
    ): DevAuthRepository
}
