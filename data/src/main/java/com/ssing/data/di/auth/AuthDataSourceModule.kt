package com.ssing.data.di.auth

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import com.ssing.core.localstorage.datastore.LocalTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTokenDataSource(
        impl: LocalTokenDataSourceImpl
    ): LocalTokenDataSource
}
