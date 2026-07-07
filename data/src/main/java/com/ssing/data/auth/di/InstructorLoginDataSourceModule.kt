package com.ssing.data.auth.di

import com.ssing.data.auth.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.auth.remote.datasource.impl.InstructorLoginDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class InstructorLoginDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindInstructorLoginDataSource(
        impl: InstructorLoginDataSourceImpl,
    ): InstructorLoginDataSource

}
