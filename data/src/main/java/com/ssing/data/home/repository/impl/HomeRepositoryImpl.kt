package com.ssing.data.home.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.home.model.ConsumerHome
import com.ssing.data.home.model.LessonCards
import com.ssing.data.home.model.Resort
import com.ssing.data.home.remote.datasource.api.HomeRemoteDataSource
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.home.remote.dto.response.LessonCardResponse
import com.ssing.data.home.remote.dto.response.ResortResponse
import com.ssing.data.home.repository.api.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: HomeRemoteDataSource,
) : HomeRepository {

    override suspend fun getConsumerHome(): Result<ConsumerHome> =
        apiResponseHandler.safeApiCall {
            dataSource.getConsumerHome()
        }.map { it.toModel() }

    private fun ConsumerHomeResponse.toModel(): ConsumerHome = ConsumerHome(
        lessonCards = this.lessonCards.map { it.toModel() },
        matchingPeopleCount = this.matchingPeopleCount,
        hasUnreadNotification = this.hasUnreadNotification,
    )

    private fun LessonCardResponse.toModel(): LessonCards = LessonCards(
        lessonId = this.lessonId,
        remainingDays = this.remainingDays,
        displayStatus = this.displayStatus,
        title = this.title,
        sport = this.sport,
        scheduledAt = this.scheduledAt,
        resort = this.resort.toModel(),
    )

    private fun ResortResponse.toModel(): Resort = Resort(
        code = this.code,
        displayName = this.displayName,
    )
}