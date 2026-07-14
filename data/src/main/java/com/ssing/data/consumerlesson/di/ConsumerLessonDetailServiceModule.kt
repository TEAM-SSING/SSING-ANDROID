package com.ssing.data.consumerlesson.di

import com.ssing.core.network.di.Auth
import com.ssing.data.consumerlesson.remote.service.ConsumerLessonDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
internal object ConsumerLessonDetailServiceModule {

    @Provides
    @Singleton
    fun provideConsumerLessonDetailService(
        @Auth retrofit: Retrofit,
    ): ConsumerLessonDetailService = retrofit.create(ConsumerLessonDetailService::class.java)
}