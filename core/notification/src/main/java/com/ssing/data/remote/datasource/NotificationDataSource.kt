package com.ssing.data.remote.datasource

import com.ssing.data.remote.dto.NotificationRequestDto

/**
 * notification 관련 원격 서버 데이터 통신을 추상화
 *
 * 백엔드 API와의 직접적인 HTTP 통신을 수행하기 전 단계로
 * 서버에 fcm 토큰을 등록하거나 삭제하는 네트워크 요청 명세 정의
 *
 * postNotificationToken을 통해 최신 기기 토큰을 서버 데이터베이스에 등록
 * deleteNotificationToken을 통해 로그아웃 또는 탈퇴 시 서버에서 기기 토큰 제거
 *
 * @param request 서버 토큰 등록 요청에 필요한 유저 및 기기 정보를 담은 데이터 전송 객체(dto)
 * @param token 서버에서 식별 및 삭제 처리할 디바이스 고유 fcm 토큰 문자열
 */

interface NotificationDataSource {
    suspend fun postNotificationToken(request: NotificationRequestDto): Result<Unit>

    suspend fun deleteNotificationToken(token: String): Result<Unit>
}