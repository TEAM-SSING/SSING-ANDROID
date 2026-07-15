package com.ssing.data.lesson.consumer.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.lesson.consumer.remote.datasource.api.ConsumerLessonDataSource
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerLessonDetailResponse
import com.ssing.data.lesson.consumer.remote.service.ConsumerLessonService
import javax.inject.Inject

internal class ConsumerLessonDataSourceImpl @Inject constructor(
    private val service: ConsumerLessonService,
) : ConsumerLessonDataSource {
    override suspend fun consumerLessonDetail(lessonId: Long): BaseResponse<ConsumerLessonDetailResponse> =
        service.getConsumerLessonDetail(lessonId)
}