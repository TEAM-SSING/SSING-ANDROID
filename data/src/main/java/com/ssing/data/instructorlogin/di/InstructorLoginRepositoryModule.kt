package com.ssing.data.instructorlogin.di

import com.ssing.data.instructorlogin.repository.api.InstructorLoginRepository
import com.ssing.data.instructorlogin.repository.impl.InstructorLoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InstructorLoginRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindInstructorLoginRepository(
        impl: InstructorLoginRepositoryImpl,
    ): InstructorLoginRepository

}