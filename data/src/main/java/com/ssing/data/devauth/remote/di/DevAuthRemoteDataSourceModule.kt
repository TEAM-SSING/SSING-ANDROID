package com.ssing.data.devauth.remote.di

import com.ssing.data.devauth.remote.datasource.api.DevAuthRemoteDataSource
import com.ssing.data.devauth.remote.datasource.impl.DevAuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DevAuthRemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindDevAuthRemoteDataSource (
        impl: DevAuthRemoteDataSourceImpl,
    ): DevAuthRemoteDataSource
}
