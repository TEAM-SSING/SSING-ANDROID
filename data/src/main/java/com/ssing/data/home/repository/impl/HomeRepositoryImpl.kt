package com.ssing.data.home.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.home.model.ConsumerHomeSummary
import com.ssing.data.home.model.InstructorHomeSummary
import com.ssing.data.home.model.ConsumerLessonCard
import com.ssing.data.home.model.InstructorLessonCard
import com.ssing.data.home.model.Resort
import com.ssing.data.home.model.ReviewSummary
import com.ssing.data.home.remote.datasource.api.HomeRemoteDataSource
import com.ssing.data.home.remote.dto.response.ConsumerHomeResponse
import com.ssing.data.home.remote.dto.response.InstructorHomeResponse
import com.ssing.data.home.remote.dto.response.ConsumerLessonCardResponse
import com.ssing.data.home.remote.dto.response.InstructorLessonCardResponse
import com.ssing.data.home.remote.dto.response.ResortResponse
import com.ssing.data.home.remote.dto.response.ReviewSummaryResponse
import com.ssing.data.home.repository.api.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: HomeRemoteDataSource,
) : HomeRepository {

    override suspend fun getConsumerHome(): Result<ConsumerHomeSummary> =
        apiResponseHandler.safeApiCall {
            dataSource.getConsumerHome()
        }.map { it.toModel() }

    override suspend fun getInstructorHome(): Result<InstructorHomeSummary> =
        apiResponseHandler.safeApiCall {
            dataSource.getInstructorHome()
        }.map { it.toModel() }

    private fun ConsumerHomeResponse.toModel(): ConsumerHomeSummary = ConsumerHomeSummary(
        lessonCards = this.lessonCards.map { it.toModel() },
        matchingPeopleCount = this.matchingPeopleCount,
        hasUnreadNotification = this.hasUnreadNotification,
    )

    private fun InstructorHomeResponse.toModel(): InstructorHomeSummary = InstructorHomeSummary(
        lessonCards = this.lessonCards.map { it.toModel() },
        matchingPeopleCount = this.matchingPeopleCount,
        hasUnreadNotification = this.hasUnreadNotification,
        instructorName = this.instructorName,
        reviewSummary = this.reviewSummary.toModel(),
    )

    private fun ConsumerLessonCardResponse.toModel(): ConsumerLessonCard = ConsumerLessonCard(
        lessonId = this.lessonId,
        remainingDays = this.remainingDays,
        displayStatus = this.displayStatus,
        title = this.title,
        sport = this.sport,
        scheduledAt = this.scheduledAt,
        resort = this.resort.toModel(),
    )

    private fun InstructorLessonCardResponse.toModel(): InstructorLessonCard = InstructorLessonCard(
        lessonId = this.lessonId,
        offerId = this.offerId,
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

    private fun ReviewSummaryResponse.toModel(): ReviewSummary = ReviewSummary(
        averageRating = this.averageRating,
        grade = this.grade,
        achievementRate = achievementRate,
    )
}
