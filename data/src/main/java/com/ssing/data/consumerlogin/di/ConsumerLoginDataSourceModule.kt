package com.ssing.data.consumerlogin.di

import com.ssing.data.consumerlogin.remote.datasource.api.ConsumerLoginDataSource
import com.ssing.data.consumerlogin.remote.datasource.impl.ConsumerLoginDataSourceImpl
import com.ssing.data.consumerlogin.remote.service.ConsumerLoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object ConsumerLoginDataSourceModule {

    @Provides
    @Singleton
    fun provideConsumerLoginService(
        retrofit: Retrofit
    ): ConsumerLoginService = retrofit.create(ConsumerLoginService::class.java)

    @Provides
    @Singleton
    fun provideConsumerLoginDataSource(
        impl: ConsumerLoginDataSourceImpl
    ): ConsumerLoginDataSource = impl
}