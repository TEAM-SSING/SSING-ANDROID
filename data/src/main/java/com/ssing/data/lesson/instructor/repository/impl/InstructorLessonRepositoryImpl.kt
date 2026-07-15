package com.ssing.data.lesson.instructor.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.common.model.LessonInfoBasic
import com.ssing.data.lesson.common.model.LessonParticipant
import com.ssing.data.lesson.common.model.Resort
import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import com.ssing.data.lesson.instructor.model.InstructorConfirmedMatchingRequest
import com.ssing.data.lesson.instructor.model.InstructorLessonDetail
import com.ssing.data.lesson.instructor.model.InstructorLessonInfo
import com.ssing.data.lesson.instructor.model.InstructorLessonSocketEvent
import com.ssing.data.lesson.instructor.model.InstructorMatchingRequest
import com.ssing.data.lesson.instructor.remote.datasource.api.InstructorLessonDataSource
import com.ssing.data.lesson.instructor.remote.dto.response.CanceledLessonInfoResponse
import com.ssing.data.lesson.instructor.remote.dto.response.CompletedLessonInfoResponse
import com.ssing.data.lesson.instructor.remote.dto.response.ConfirmedMatchingRequestResponse
import com.ssing.data.lesson.instructor.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.instructor.remote.dto.response.MatchingRequestResponse
import com.ssing.data.lesson.instructor.remote.dto.response.ParticipantResponse
import com.ssing.data.lesson.instructor.remote.dto.response.ScheduledLessonInfoResponse
import com.ssing.data.lesson.instructor.repository.api.InstructorLessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class InstructorLessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: InstructorLessonDataSource,
    private val socketDataSource: LessonSocketDataSource,
) : InstructorLessonRepository {

    override val socketEvents: Flow<InstructorLessonSocketEvent> = socketDataSource.event
        .filter { it.recipientRole == RECIPIENT_INSTRUCTOR }
        .filter { it.eventType in RELEVANT_EVENT_TYPES }
        .map { InstructorLessonSocketEvent(lessonId = it.lessonId, lessonStatus = it.lessonStatus) }

    override val socketState: StateFlow<SocketState> = socketDataSource.socketState

    override fun connectSocket() {
        socketDataSource.connect()
    }

    override suspend fun disconnectSocket() {
        socketDataSource.disconnect()
    }

    override suspend fun fetchInstructorLessonDetail(lessonId: Long): Result<InstructorLessonDetail> =
        apiResponseHandler.safeApiCall {
            dataSource.instructorLessonDetail(lessonId = lessonId)
        }.map { it.toModel() }

    private fun InstructorLessonDetailResponse.toModel(): InstructorLessonDetail =
        when (this) {
            is InstructorLessonDetailResponse.Confirmed -> InstructorLessonDetail.Confirmed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                confirmedCount = statusInfo.confirmedCount,
                requiredCount = statusInfo.requiredCount,
                currentActorConfirmed = statusInfo.currentActorConfirmed,
                instructorConfirmed = statusInfo.instructorConfirmed,
                scheduledAt = lessonInfo.scheduledAt,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )

            is InstructorLessonDetailResponse.InProgress -> InstructorLessonDetail.InProgress(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                serverTime = statusInfo.serverTime,
                actualStartedAt = statusInfo.actualStartedAt,
                expectedEndedAt = statusInfo.expectedEndedAt,
                elapsedSeconds = statusInfo.elapsedSeconds,
                remainingSeconds = statusInfo.remainingSeconds,
                scheduledAt = lessonInfo.scheduledAt,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )

            is InstructorLessonDetailResponse.Completed -> InstructorLessonDetail.Completed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
                actualStartedAt = lessonInfo.actualStartedAt,
                actualEndedAt = lessonInfo.actualEndedAt,
                actualDurationMinutes = lessonInfo.actualDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )

            is InstructorLessonDetailResponse.Canceled -> InstructorLessonDetail.Canceled(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                canceledAt = cancelInfo.canceledAt,
                canceledByMemberId = cancelInfo.canceledBy.memberId,
                canceledByName = cancelInfo.canceledBy.name,
                cancelReason = cancelInfo.cancelReason,
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )
        }

    private fun ScheduledLessonInfoResponse.toModel() = InstructorLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        totalLessonPrice = totalLessonPrice,
    )

    private fun CompletedLessonInfoResponse.toModel() = InstructorLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        totalLessonPrice = totalLessonPrice,
    )

    private fun CanceledLessonInfoResponse.toModel() = InstructorLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        totalLessonPrice = totalLessonPrice,
    )

    private fun ParticipantResponse.toModel() = LessonParticipant(participantId, gender, age)

    private fun ConfirmedMatchingRequestResponse.toModel() = InstructorConfirmedMatchingRequest(
        matchingRequestId = matchingRequestId,
        representativeMemberId = representativeMemberId,
        representativeMemberName = representativeMemberName,
        headcount = headcount,
        participants = participants.map { it.toModel() },
        teamLessonPrice = teamLessonPrice,
        startConfirmed = startConfirmed,
    )

    private fun MatchingRequestResponse.toModel() = InstructorMatchingRequest(
        matchingRequestId = matchingRequestId,
        representativeMemberId = representativeMemberId,
        representativeMemberName = representativeMemberName,
        headcount = headcount,
        participants = participants.map { it.toModel() },
        teamLessonPrice = teamLessonPrice,
    )

    companion object {
        private const val RECIPIENT_INSTRUCTOR = "INSTRUCTOR"
        private val RELEVANT_EVENT_TYPES = setOf(
            "LESSON_START_CONFIRMATION_UPDATED",
            "LESSON_STARTED",
            "LESSON_COMPLETED",
            "LESSON_CANCELED",
        )
    }
}