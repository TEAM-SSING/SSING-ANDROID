package com.ssing.data.payment.di

import com.ssing.data.payment.remote.datasource.api.PaymentRemoteDataSource
import com.ssing.data.payment.remote.datasource.impl.PaymentRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PaymentDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindPaymentRemoteDataSource(
        impl: PaymentRemoteDataSourceImpl,
    ): PaymentRemoteDataSource
}

