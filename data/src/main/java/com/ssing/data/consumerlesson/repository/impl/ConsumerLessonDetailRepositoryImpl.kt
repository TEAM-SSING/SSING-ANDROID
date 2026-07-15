package com.ssing.data.consumerlesson.repository.impl

import com.ssing.core.network.socket.SocketState
import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.consumerlesson.exception.ConsumerLessonDetailException
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
import com.ssing.data.consumerlesson.model.ConsumerLessonSocketEvent
import com.ssing.data.consumerlesson.model.InstructorProfile
import com.ssing.data.consumerlesson.model.LessonInfo
import com.ssing.data.consumerlesson.model.LessonMatchingRequest
import com.ssing.data.consumerlesson.model.LessonParticipant
import com.ssing.data.consumerlesson.remote.datasource.api.ConsumerLessonDetailDataSource
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
import kotlinx.coroutines.flow.onStart
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

    private fun ConsumerLessonDetailResponse.toModel(): ConsumerLessonDetail {
        val dtoLessonInfo = lessonInfo!!
        val lessonInfo = dtoLessonInfo.toModel()
        val instructorProfile = instructorProfile!!.toModel()

        return when (lessonStatus) {
            "CONFIRMED" -> {
                val status = statusInfo!!

                ConsumerLessonDetail.Confirmed(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    confirmedCount = status.confirmedCount!!,
                    requiredCount = status.requiredCount!!,
                    currentActorConfirmed = status.currentActorConfirmed!!,
                    instructorConfirmed = status.instructorConfirmed!!,
                    scheduledDurationMinutes = dtoLessonInfo.scheduledDurationMinutes!!,
                    lessonMatchingRequest = matchingRequests.orEmpty().map { it.toModel() },
                )
            }

            "IN_PROGRESS" -> {
                val status = statusInfo!!

                ConsumerLessonDetail.InProgress(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    serverTime = status.serverTime!!,
                    actualStartedAt = status.actualStartedAt!!,
                    expectedEndedAt = status.expectedEndedAt!!,
                    elapsedSeconds = status.elapsedSeconds!!,
                    remainingSeconds = status.remainingSeconds!!,
                    scheduledDurationMinutes = dtoLessonInfo.scheduledDurationMinutes!!,
                    lessonMatchingRequest = matchingRequests.orEmpty().map { it.toModel() },
                )
            }

            "COMPLETED" -> ConsumerLessonDetail.Completed(
                lessonId = lessonId,
                lessonInfo = lessonInfo,
                instructorProfile = instructorProfile,
                lessonDurationMinutes = dtoLessonInfo.lessonDurationMinutes!!,
                actualStartedAt = dtoLessonInfo.actualStartedAt!!,
                actualEndedAt = dtoLessonInfo.actualEndedAt!!,
                actualDurationMinutes = dtoLessonInfo.actualDurationMinutes!!,
            )

            "CANCELED" -> {
                val cancel = cancelInfo!!

                ConsumerLessonDetail.Canceled(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    canceledAt = cancel.canceledAt,
                    canceledByMemberId = cancel.canceledBy.memberId,
                    canceledByName = cancel.canceledBy.name,
                    cancelReason = cancel.cancelReason,
                    lessonDurationMinutes = dtoLessonInfo.lessonDurationMinutes!!,
                )
            }

            else -> throw ConsumerLessonDetailException.LessonInvalidState(
                serverCode = null,
                message = "Unknown lessonStatus",
                requestId = null,
            )
        }
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