package com.ssing.data.repository.impl

import com.ssing.core.network.token.TokenAccessManager
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequest
import com.ssing.data.repository.NotificationRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * NotificationRepository 실제 구현체
 *
 * 토큰을 언제 서버에 보낼지 타이밍 제어
 * NotificationDataSource를 호출하여 최종적으로 행동을 수행
 *
 * 유저의 로그인 상태를 확인하여 인증된 사용자일 때만 서버에 푸시 토큰 등록
 * 비로그인 상태일 경우 작업을 패스
 *
 * @param dataSource 실제 서버 통신을 담당하는 알림 데이터 소스 객체
 * @param tokenAccessManager 현재 유저의 로그인 상태(액세스 토큰 존재 여부)를 검사하기 위한 매니저 객체
 * @param token 서버에 등록하거나 검사할 디바이스 고유 fcm 토큰 문자열
 */

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource,
    private val tokenAccessManager: TokenAccessManager,
) : NotificationRepository {

    override suspend fun saveNotificationToken(token: String): Result<Unit> {
        val accessToken = tokenAccessManager.getAccessToken()
        if (accessToken == null) {
            Timber.d("로그인 상태가 아니라 FCM 토큰을 서버에 전송하지 않음")
            return Result.success(Unit)
        }

        return dataSource.postNotificationToken(NotificationRequest(token = token))
    }
}