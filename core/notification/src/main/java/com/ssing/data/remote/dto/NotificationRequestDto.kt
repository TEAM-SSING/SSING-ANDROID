package com.ssing.data.remote.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * fcm 토큰 서버 등록 요청을 위한 네트워크 데이터 전송 객체(dto)
 *
 * 백엔드 서버에 기기 토큰을 보낼 때 양식을 맞춰주기 위해 사용
 * 플랫폼 정보는 안드로이드ANDROID로 자동 설정
 *
 * 유저 로그인/회원가입 완료 후 서버에 푸시 토큰 전송 시 활용
 *
 * @param token 서버에 저장할 디바이스 고유 fcm 토큰
 * @param platform 알림을 수신할 기기의 OS 종류
 */

@Serializable
@OptIn(InternalSerializationApi::class)
data class NotificationRequestDto(
    @SerialName("token")
    val token: String,
    @SerialName("platform")
    val platform: String = ANDROID,
) {
    companion object {
        private const val ANDROID = "ANDROID"
    }
}