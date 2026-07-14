package com.ssing.data.matching.common.di

import com.ssing.data.matching.common.remote.datasource.api.MatchingSocketDataSource
import com.ssing.data.matching.common.remote.datasource.impl.MatchingSocketDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MatchingSocketDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMatchingSocketDataSource(
        impl: MatchingSocketDataSourceImpl,
    ): MatchingSocketDataSource
}
