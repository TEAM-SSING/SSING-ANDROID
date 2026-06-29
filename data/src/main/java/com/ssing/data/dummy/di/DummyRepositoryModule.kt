package com.ssing.data.dummy.di

import com.ssing.data.dummy.repository.api.DummyRepository
import com.ssing.data.dummy.repository.impl.DummyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DummyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDummyRepository(
        impl: DummyRepositoryImpl
    ): DummyRepository
}