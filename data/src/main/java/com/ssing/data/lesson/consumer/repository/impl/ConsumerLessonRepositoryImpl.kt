package com.ssing.data.lesson.consumer.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.common.model.LessonInfoBasic
import com.ssing.data.lesson.common.model.LessonParticipant
import com.ssing.data.lesson.common.model.Resort
import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import com.ssing.data.lesson.consumer.model.ConsumerConfirmedMatchingRequest
import com.ssing.data.lesson.consumer.model.ConsumerLessonDetail
import com.ssing.data.lesson.consumer.model.ConsumerLessonInfo
import com.ssing.data.lesson.consumer.model.ConsumerLessonSocketEvent
import com.ssing.data.lesson.consumer.model.ConsumerMatchingRequest
import com.ssing.data.lesson.consumer.model.InstructorProfile
import com.ssing.data.lesson.consumer.remote.datasource.api.ConsumerLessonDataSource
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerCanceledLessonInfoResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerCompletedLessonInfoResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerLessonDetailResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerMatchingRequestResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerScheduledLessonInfoResponse
import com.ssing.data.lesson.consumer.remote.dto.response.InstructorProfileResponse
import com.ssing.data.lesson.consumer.remote.dto.response.ParticipantResponse
import com.ssing.data.lesson.consumer.repository.api.ConsumerLessonRepository
import com.ssing.data.lesson.consumer.remote.dto.response.ConsumerConfirmedMatchingRequestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ConsumerLessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerLessonDataSource,
    private val socketDataSource: LessonSocketDataSource,
) : ConsumerLessonRepository {

    override val socketEvents: Flow<ConsumerLessonSocketEvent> = socketDataSource.event
        .filter { it.recipientRole == RECIPIENT_CONSUMER }
        .filter { it.eventType in RELEVANT_EVENT_TYPES }
        .map { ConsumerLessonSocketEvent(lessonId = it.lessonId, lessonStatus = it.lessonStatus) }

    override val socketState: StateFlow<SocketState> = socketDataSource.socketState

    override fun connectSocket() {
        socketDataSource.connect()
    }

    override suspend fun disconnectSocket() {
        socketDataSource.disconnect()
    }

    override suspend fun fetchConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail> =
        apiResponseHandler.safeApiCall {
            dataSource.consumerLessonDetail(lessonId = lessonId)
        }.map { it.toModel() }

    private fun ConsumerLessonDetailResponse.toModel(): ConsumerLessonDetail =
        when (this) {
            is ConsumerLessonDetailResponse.Confirmed -> ConsumerLessonDetail.Confirmed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                confirmedCount = statusInfo.confirmedCount,
                requiredCount = statusInfo.requiredCount,
                currentActorConfirmed = statusInfo.currentActorConfirmed,
                instructorConfirmed = statusInfo.instructorConfirmed,
                scheduledAt = lessonInfo.scheduledAt,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )

            is ConsumerLessonDetailResponse.InProgress -> ConsumerLessonDetail.InProgress(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                serverTime = statusInfo.serverTime,
                actualStartedAt = statusInfo.actualStartedAt,
                expectedEndedAt = statusInfo.expectedEndedAt,
                elapsedSeconds = statusInfo.elapsedSeconds,
                remainingSeconds = statusInfo.remainingSeconds,
                scheduledAt = lessonInfo.scheduledAt,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
                matchingRequests = matchingRequests.map { it.toModel() },
            )

            is ConsumerLessonDetailResponse.Completed -> ConsumerLessonDetail.Completed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
                actualStartedAt = lessonInfo.actualStartedAt,
                actualEndedAt = lessonInfo.actualEndedAt,
                actualDurationMinutes = lessonInfo.actualDurationMinutes,
            )

            is ConsumerLessonDetailResponse.Canceled -> ConsumerLessonDetail.Canceled(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                canceledAt = cancelInfo.canceledAt,
                canceledByMemberId = cancelInfo.canceledBy.memberId,
                canceledByName = cancelInfo.canceledBy.name,
                cancelReason = cancelInfo.cancelReason,
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
            )
        }

    private fun ConsumerScheduledLessonInfoResponse.toModel() = ConsumerLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        myTeamLessonPrice = myTeamLessonPrice,
    )

    private fun ConsumerCompletedLessonInfoResponse.toModel() = ConsumerLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        myTeamLessonPrice = myTeamLessonPrice,
    )

    private fun ConsumerCanceledLessonInfoResponse.toModel() = ConsumerLessonInfo(
        basic = LessonInfoBasic(
            representativeConsumerNames = representativeConsumerNames,
            totalHeadcount = totalHeadcount,
            resort = Resort(resort.code, resort.displayName),
            sport = sport,
            lessonLevel = lessonLevel,
        ),
        myTeamLessonPrice = myTeamLessonPrice,
    )

    private fun InstructorProfileResponse.toModel() = InstructorProfile(
        instructorId = instructorId,
        name = name,
        gender = gender,
        birthYear = birthYear,
        level = level,
        profileImageUrl = profileImageUrl,
    )

    private fun ParticipantResponse.toModel() = LessonParticipant(participantId, gender, age)

    private fun ConsumerConfirmedMatchingRequestResponse.toModel() = ConsumerConfirmedMatchingRequest(
        matchingRequestId = matchingRequestId,
        representativeMemberId = representativeMemberId,
        representativeMemberName = representativeMemberName,
        headcount = headcount,
        participants = participants.map { it.toModel() },
        startConfirmed = startConfirmed,
    )

    private fun ConsumerMatchingRequestResponse.toModel() = ConsumerMatchingRequest(
        matchingRequestId = matchingRequestId,
        representativeMemberId = representativeMemberId,
        representativeMemberName = representativeMemberName,
        headcount = headcount,
        participants = participants.map { it.toModel() },
    )

    companion object {
        private const val RECIPIENT_CONSUMER = "CONSUMER"
        private val RELEVANT_EVENT_TYPES = setOf(
            "LESSON_START_CONFIRMATION_UPDATED",
            "LESSON_STARTED",
            "LESSON_COMPLETED",
            "LESSON_CANCELED",
        )
    }

}
