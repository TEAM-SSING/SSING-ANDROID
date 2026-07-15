package com.ssing.data.payment.di

import com.ssing.core.network.di.Auth
import com.ssing.data.payment.remote.service.PaymentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
internal object PaymentServiceModule {

    @Provides
    @Singleton
    fun providePaymentService(
        @Auth retrofit: Retrofit,
    ): PaymentService = retrofit.create(PaymentService::class.java)
}
