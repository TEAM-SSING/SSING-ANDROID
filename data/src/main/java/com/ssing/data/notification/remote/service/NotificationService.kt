package com.ssing.data.notification.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.notification.remote.dto.response.NotificationsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NotificationService {

    /**
     * 현재 로그인한 회원의 알림 목록을 최신순으로 조회한다. (커서 기반)
     *
     * @param cursor 이전 응답의 nextCursor. 첫 페이지는 null(파라미터 생략).
     * @param size 조회 개수(1~100, 기본 20).
     */
    @GET("/api/v1/notifications")
    suspend fun getNotifications(
        @Query("cursor") cursor: String?,
        @Query("size") size: Int,
    ): BaseResponse<NotificationsResponse>
}
