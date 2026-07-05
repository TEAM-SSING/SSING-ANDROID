package com.ssing.data.instructorlogin.di

import com.ssing.data.instructorlogin.remote.datasource.api.InstructorLoginDataSource
import com.ssing.data.instructorlogin.remote.datasource.impl.InstructorLoginDataSourceImpl
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
