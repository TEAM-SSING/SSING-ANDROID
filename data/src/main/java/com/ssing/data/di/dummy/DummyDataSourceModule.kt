package com.ssing.data.di.dummy

import com.ssing.data.dummy.remote.datasource.api.DummyDataSource
import com.ssing.data.dummy.remote.datasource.impl.DummyDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DummyDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindDummyDataSource(
        impl: DummyDataSourceImpl
    ): DummyDataSource
}
