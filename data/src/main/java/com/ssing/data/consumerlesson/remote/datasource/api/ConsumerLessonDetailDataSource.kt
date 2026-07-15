package com.ssing.data.consumerlesson.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailResponse

internal interface ConsumerLessonDetailDataSource {
    suspend fun getConsumerLessonDetail(lessonId: Long): BaseResponse<ConsumerLessonDetailResponse>
}