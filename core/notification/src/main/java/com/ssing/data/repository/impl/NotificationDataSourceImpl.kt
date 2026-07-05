package com.ssing.data.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.remote.datasource.NotificationDataSource
import com.ssing.data.remote.dto.NotificationRequest
import com.ssing.data.repository.api.NotificationApi
import javax.inject.Inject

/**
 * NotificationDataSource의 구현체
 *
 * NotificationApi를 호출하여 서버와 통신
 * 발생할 수 있는 네트워크 에러를 apiResponseHandler로 감싸서 반환
 *
 * 실제 서버 API 호출 및 성공/실패 여부를 Result 형태로 가공하여 상위 레이어로 전달
 *
 * @param api 서버의 알림 관련 통신 주소들을 모아놓은 창구 객체
 * @param apiResponseHandler 네트워크 에러 및 예외 상황을 안전하게 가공해 주는 처리기
 * @param request 서버 토큰 등록 요청 시 필요한 데이터(토큰, 플랫폼) 묶음 박스
 * @param token 서버에서 삭제할 디바이스 고유 fcm 토큰 문자열
 */

class NotificationDataSourceImpl @Inject constructor(
    private val api: NotificationApi,
    private val apiResponseHandler: ApiResponseHandler,
) : NotificationDataSource {

    override suspend fun postNotificationToken(request: NotificationRequest): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.postNotificationToken(request) }

    override suspend fun deleteNotificationToken(token: String): Result<Unit> =
        apiResponseHandler.safeUnitApiCall { api.deleteNotificationToken(token) }
}
