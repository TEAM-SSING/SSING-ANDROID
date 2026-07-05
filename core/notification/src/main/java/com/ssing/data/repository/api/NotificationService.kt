package com.ssing.data.repository.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.remote.dto.NotificationRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 서버의 알림 관련 API 주소들을 모음 인터페이스
 *
 * Retrofit 라이브러리를 통해 백엔드 서버와 실제 통신을 주고 받음
 *
 * postNotificationToken -> 서버에 새 푸시 토큰 등록 요청
 * deleteNotificationToken -> 로그아웃/탈퇴 시 서버에서 푸시 토큰 삭제 요청
 *
 * @param request 서버에 등록할 토큰과 플랫폼 정보가 담긴 박스 객체
 * @param token 서버에서 찾아서 지울 디바이스 고유 fcm 토큰 문자열
 */

interface NotificationService {
    @POST("notifications/token")
    suspend fun postNotificationToken(
        @Body request: NotificationRequest,
    ): BaseResponse<Unit>

    @DELETE("notifications/token")
    suspend fun deleteNotificationToken(
        @Query("token") token: String,
    ): BaseResponse<Unit>
}
