package com.ssing.data.di

import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.repository.NotificationRepository
import com.ssing.data.repository.api.NotificationApi
import com.ssing.data.repository.impl.NotificationDataSourceImpl
import com.ssing.data.repository.impl.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * notification 도메인 관련 의존성 주입 설정을 위한 Hilt 모듈 클래스
 *
 * 앱 전체 수명 동안 단일 인스턴스(Singleton)로 유지
 * 인터페이스와 impl 간의 바인딩 및 Retrofit API 서비스 공급 담당
 *
 * 인터페이스 주입 요청 시 NotificationDataSourceImpl, NotificationRepositoryImpl 제공
 * Retrofit 기반 NotificationApi 객체를 생성 및 공급
 *
 * @param impl 인터페이스에 주입할 실제 비즈니스 로직 및 데이터 처리 구현체
 * @param retrofit 네트워크 통신 및 API 서비스 객체 생성
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindNotificationDataSource(
        impl: NotificationDataSourceImpl
    ): NotificationDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    companion object {
        @Provides
        @Singleton
        fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
            retrofit.create(NotificationApi::class.java)
    }
}