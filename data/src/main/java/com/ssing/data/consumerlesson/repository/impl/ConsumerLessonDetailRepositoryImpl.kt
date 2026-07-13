package com.ssing.data.consumerlesson.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.consumerlesson.exception.ConsumerLessonDetailException
import com.ssing.data.consumerlesson.model.ConsumerLessonDetail
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
import javax.inject.Inject

internal class ConsumerLessonDetailRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: ConsumerLessonDetailDataSource,
) : ConsumerLessonDetailRepository {

    override suspend fun getConsumerLessonDetail(lessonId: Long): Result<ConsumerLessonDetail> =
        apiResponseHandler.safeApiCall {
            dataSource.getConsumerLessonDetail(lessonId)
        }.mapCatching { it.toModel() }

    private fun ConsumerLessonDetailResponse.toModel(): ConsumerLessonDetail {
        val dtoLessonInfo = requireNotNull(lessonInfo) { "강습 정보가 없습니다." }
        val lessonInfo = dtoLessonInfo.toModel()
        val instructorProfile = requireNotNull(instructorProfile) { "강사 프로필 정보가 없습니다." }.toModel()

        return when (lessonStatus) {
            "CONFIRMED" -> {
                val status = requireNotNull(statusInfo) { "강습 상태 정보가 없습니다." }

                ConsumerLessonDetail.Confirmed(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    confirmedCount = requireNotNull(status.confirmedCount),
                    requiredCount = requireNotNull(status.requiredCount),
                    currentActorConfirmed = requireNotNull(status.currentActorConfirmed),
                    instructorConfirmed = requireNotNull(status.instructorConfirmed),
                    scheduledDurationMinutes = requireNotNull(dtoLessonInfo.scheduledDurationMinutes),
                    lessonMatchingRequest = matchingRequests.orEmpty().map { it.toModel() },
                )
            }

            "IN_PROGRESS" -> {
                val status = requireNotNull(statusInfo) { "강습 상태 정보가 없습니다." }

                ConsumerLessonDetail.InProgress(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    serverTime = requireNotNull(status.serverTime),
                    actualStartedAt = requireNotNull(status.actualStartedAt),
                    expectedEndedAt = requireNotNull(status.expectedEndedAt),
                    elapsedSeconds = requireNotNull(status.elapsedSeconds),
                    remainingSeconds = requireNotNull(status.remainingSeconds),
                    scheduledDurationMinutes = requireNotNull(dtoLessonInfo.scheduledDurationMinutes),
                    lessonMatchingRequest = matchingRequests.orEmpty().map { it.toModel() },
                )
            }

            "COMPLETED" -> ConsumerLessonDetail.Completed (
                lessonId = lessonId,
                lessonInfo = lessonInfo,
                instructorProfile = instructorProfile,
                lessonDurationMinutes = requireNotNull(dtoLessonInfo.lessonDurationMinutes),
                actualStartedAt = requireNotNull(dtoLessonInfo.actualStartedAt),
                actualEndedAt = requireNotNull(dtoLessonInfo.actualEndedAt),
                actualDurationMinutes = requireNotNull(dtoLessonInfo.actualDurationMinutes),
            )

            "CANCELED" -> {
                val cancel = requireNotNull(cancelInfo) { "취소 정보가 없습니다." }

                ConsumerLessonDetail.Canceled(
                    lessonId = lessonId,
                    lessonInfo = lessonInfo,
                    instructorProfile = instructorProfile,
                    canceledAt = cancel.canceledAt,
                    canceledByMemberId = cancel.canceledBy.memberId,
                    canceledByName = cancel.canceledBy.name,
                    cancelReason = cancel.cancelReason,
                    lessonDurationMinutes = requireNotNull(dtoLessonInfo.lessonDurationMinutes),
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
}