package com.ssing.data.instructormatching.di

import com.ssing.data.instructormatching.remote.datasource.api.InstructorMatchingRemoteDataSource
import com.ssing.data.instructormatching.remote.datasource.impl.InstructorMatchingRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorMatchingDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindInstructorMatchingRemoteDataSource(
        impl: InstructorMatchingRemoteDataSourceImpl,
    ): InstructorMatchingRemoteDataSource
}
