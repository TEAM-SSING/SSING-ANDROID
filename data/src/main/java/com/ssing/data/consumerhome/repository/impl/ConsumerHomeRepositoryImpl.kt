package com.ssing.data.consumerhome.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.consumerhome.model.ConsumerHome
import com.ssing.data.consumerhome.model.LessonCards
import com.ssing.data.consumerhome.model.Resort
import com.ssing.data.consumerhome.remote.datasource.api.ConsumerHomeRemoteDataSource
import com.ssing.data.consumerhome.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.consumerhome.remote.dto.response.LessonCardResponse
import com.ssing.data.consumerhome.remote.dto.response.ResortResponse
import com.ssing.data.consumerhome.repository.api.ConsumerHomeRepository
import javax.inject.Inject

internal class ConsumerHomeRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerHomeRemoteDataSource,
): ConsumerHomeRepository {

    override suspend fun getConsumerHome(): Result<ConsumerHome> =
        apiResponseHandler.safeApiCall{
            dataSource.getConsumerHome()
        }.map{ it.toModel() }

    private fun ConsumerHomeResponse.toModel(): ConsumerHome = ConsumerHome(
        lessonCards = lessonCards.map { it.toModel() },
        matchingPeopleCount = this.matchingPeopleCount,
        hasUnreadNotification = this.hasUnreadNotification,
    )

    private fun LessonCardResponse.toModel() = LessonCards(
        lessonId = lessonId,
        remainingDays = remainingDays,
        displayStatus = displayStatus,
        title = title,
        sport = sport,
        scheduledAt = scheduledAt,
        resort = resort.toModel(),
    )

    private fun ResortResponse.toModel() = Resort(
        code = code,
        displayName = displayName,
    )
}