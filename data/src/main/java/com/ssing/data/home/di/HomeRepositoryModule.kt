package com.ssing.data.home.di

import com.ssing.data.home.repository.api.HomeRepository
import com.ssing.data.home.repository.impl.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConsumerRepository(
        impl: HomeRepositoryImpl,
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindInstructorRepository(
        impl: HomeRepositoryImpl,
    ): HomeRepository
}
