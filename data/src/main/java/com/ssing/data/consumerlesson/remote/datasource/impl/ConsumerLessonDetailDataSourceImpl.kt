package com.ssing.data.consumerlesson.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.consumerlesson.remote.datasource.api.ConsumerLessonDetailDataSource
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailResponse
import com.ssing.data.consumerlesson.remote.service.ConsumerLessonDetailService
import javax.inject.Inject

internal class ConsumerLessonDetailDataSourceImpl @Inject constructor(
    private val service: ConsumerLessonDetailService,
) : ConsumerLessonDetailDataSource {
    override suspend fun getConsumerLessonDetail(lessonId: Long): BaseResponse<ConsumerLessonDetailResponse> =
        service.getConsumerLessonDetail(lessonId)
}