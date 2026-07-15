package com.ssing.data.lesson.consumer.remote.service

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerLessonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ConsumerLessonService {
    @GET("api/v1/consumer/lessons/{lessonId}")
    suspend fun getConsumerLessonDetail(
        @Path("lessonId") lessonId: Long,
    ): BaseResponse<ConsumerLessonDetailResponse>
}