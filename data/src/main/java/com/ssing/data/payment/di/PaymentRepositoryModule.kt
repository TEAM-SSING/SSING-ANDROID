package com.ssing.data.payment.di

import com.ssing.data.payment.repository.api.PaymentRepository
import com.ssing.data.payment.repository.impl.PaymentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PaymentRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl,
    ): PaymentRepository
}
