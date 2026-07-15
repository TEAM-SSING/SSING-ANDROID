package com.ssing.data.consumerlesson.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.consumerlesson.model.ConsumerLessonSocketEvent
import com.ssing.data.consumerlesson.model.InstructorProfile
import com.ssing.data.consumerlesson.model.LessonInfo
import com.ssing.data.consumerlesson.model.LessonMatchingRequest
import com.ssing.data.consumerlesson.model.LessonParticipant
import com.ssing.data.consumerlesson.remote.datasource.api.ConsumerLessonDetailDataSource
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailBeforeResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailCanceledResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailCompletedResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailOngoingResponse
import com.ssing.data.consumerlesson.remote.dto.response.ConsumerLessonDetailResponse
import com.ssing.data.consumerlesson.remote.dto.response.instructorprofile.ConsumerLessonInstructorProfile
import com.ssing.data.consumerlesson.remote.dto.response.lessoninfo.ConsumerLessonInfo
import com.ssing.data.consumerlesson.remote.dto.response.matchingrequest.ConsumerLessonMatchingRequest
import com.ssing.data.consumerlesson.remote.dto.response.matchingrequest.Participant
import com.ssing.data.consumerlesson.repository.api.ConsumerLessonDetailRepository
import com.ssing.data.lesson.common.remote.datasource.api.LessonSocketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ConsumerLessonDetailRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerLessonDetailDataSource,
    private val socketDataSource: LessonSocketDataSource,
) : ConsumerLessonDetailRepository {

    private val relevantEventTypes = setOf(
        "LESSON_START_CONFIRMATION_UPDATED",
        "LESSON_STARTED",
        "LESSON_COMPLETED",
        "LESSON_CANCELED",
    )

    override val socketEvents: Flow<ConsumerLessonSocketEvent> = socketDataSource.event
        .filter { it.recipientRole == RECIPIENT_CONSUMER }
        .filter { it.eventType in relevantEventTypes }
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
            dataSource.getConsumerLessonDetail(lessonId)
        }.mapCatching { it.toModel() }

    private fun ConsumerLessonDetailResponse.toModel(): ConsumerLessonDetail =
        when (this) {
            is ConsumerLessonDetailBeforeResponse -> ConsumerLessonDetail.Confirmed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                confirmedCount = statusInfo.confirmedCount!!,
                requiredCount = statusInfo.requiredCount!!,
                currentActorConfirmed = statusInfo.currentActorConfirmed!!,
                instructorConfirmed = statusInfo.instructorConfirmed!!,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes!!,
                lessonMatchingRequest = matchingRequests.map { it.toModel() },
            )

            is ConsumerLessonDetailOngoingResponse -> ConsumerLessonDetail.InProgress(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                serverTime = statusInfo.serverTime!!,
                actualStartedAt = statusInfo.actualStartedAt!!,
                expectedEndedAt = statusInfo.expectedEndedAt!!,
                elapsedSeconds = statusInfo.elapsedSeconds!!,
                remainingSeconds = statusInfo.remainingSeconds!!,
                scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes!!,
                lessonMatchingRequest = matchingRequests.map { it.toModel() },
            )

            is ConsumerLessonDetailCompletedResponse -> ConsumerLessonDetail.Completed(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes!!,
                actualStartedAt = lessonInfo.actualStartedAt!!,
                actualEndedAt = lessonInfo.actualEndedAt!!,
                actualDurationMinutes = lessonInfo.actualDurationMinutes!!,
            )

            is ConsumerLessonDetailCanceledResponse -> ConsumerLessonDetail.Canceled(
                lessonId = lessonId,
                lessonInfo = lessonInfo.toModel(),
                instructorProfile = instructorProfile.toModel(),
                canceledAt = cancelInfo.canceledAt,
                canceledByMemberId = cancelInfo.canceledBy.memberId,
                canceledByName = cancelInfo.canceledBy.name,
                cancelReason = cancelInfo.cancelReason,
                lessonDurationMinutes = lessonInfo.lessonDurationMinutes!!,
            )
        }

    private fun ConsumerLessonInfo.toModel(): LessonInfo = LessonInfo(
        representativeConsumerNames = representativeConsumerNames,
        totalHeadcount = totalHeadcount,
        resortCode = resort.code,
        resortDisplayName = resort.displayName,
        sport = sport,
        lessonLevel = lessonLevel,
        myTeamLessonPrice = myTeamLessonPrice,
    )

    private fun ConsumerLessonInstructorProfile.toModel(): InstructorProfile =
        InstructorProfile(
            instructorId = instructorId,
            name = name,
            gender = gender,
            birthYear = birthYear,
            level = level,
            profileImageUrl = profileImageUrl,
        )

    private fun ConsumerLessonMatchingRequest.toModel(): LessonMatchingRequest =
        LessonMatchingRequest(
            matchingRequestId = matchingRequestId,
            representativeMemberId = representativeMemberId,
            representativeMemberName = representativeMemberName,
            headcount = headcount,
            participants = participants.map { it.toModel() },
            startConfirmed = startConfirmed ?: false,
        )

    private fun Participant.toModel(): LessonParticipant = LessonParticipant(
        participantId = participantId,
        gender = gender,
        age = age,
    )

    companion object {
        private const val RECIPIENT_CONSUMER = "CONSUMER"
    }
}