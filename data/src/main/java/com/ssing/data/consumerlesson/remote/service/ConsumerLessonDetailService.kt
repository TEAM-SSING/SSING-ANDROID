package com.ssing.data.consumerlesson.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ConsumerLessonDetailService {
    @GET("/api/v1/consumer/lessons/{lessonId}")
    suspend fun getConsumerLessonDetail(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<ConsumerLessonDetailResponse>
}