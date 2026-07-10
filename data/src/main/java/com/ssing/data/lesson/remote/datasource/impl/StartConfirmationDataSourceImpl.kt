package com.ssing.data.lesson.remote.datasource.impl

import com.ssing.data.lesson.remote.datasource.api.StartConfirmationDataSource
import com.ssing.data.lesson.remote.service.StartConfirmationService
import javax.inject.Inject

internal class StartConfirmationDataSourceImpl @Inject constructor(
    private val service: StartConfirmationService,
) : StartConfirmationDataSource {

    override suspend fun startConfirmation(lessonId: Long) =
        service.startConfirmation(lessonId)
}