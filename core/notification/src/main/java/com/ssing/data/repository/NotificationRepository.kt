package com.ssing.data.repository

/**
 * Notification 도메인의 비즈니스 로직 명세를 정의한 레포지토리 인터페이스
 *
 * 외부 모듈이나 유스케이스에서 실제 서버 통신 구조를 모르더라도, 안전하게 알림 관련 행위를 요청할 수 있도록 돕는 역할
 *
 * 로그인 상태를 내부적으로 체크하여 인증된 유저의 토큰만 서버에 저장
 *
 * @param token 서버에 등록 및 동기화할 디바이스 고유 fcm 토큰 문자열
 */

interface NotificationRepository {
    suspend fun saveNotificationToken(token: String): Result<Unit>
}