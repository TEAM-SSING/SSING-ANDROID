package com.ssing.data.lesson.consumer.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerLessonDetailResponse

internal interface ConsumerLessonDataSource {
    suspend fun consumerLessonDetail(
        lessonId: Long
    ): BaseResponse<ConsumerLessonDetailResponse>
}