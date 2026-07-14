package com.ssing.data.lesson.repository.impl

import com.ssing.core.network.util.ApiResponseHandler
import com.ssing.data.lesson.instructorlesson.repository.api.InstructorLessonRepository
import com.ssing.data.lesson.model.InstructorLessonDetailBefore
import com.ssing.data.lesson.model.InstructorLessonDetailCanceled
import com.ssing.data.lesson.model.InstructorLessonDetailCompleted
import com.ssing.data.lesson.model.InstructorLessonDetailOngoing
import com.ssing.data.lesson.model.InstructorLessonDetailRequestResult
import com.ssing.data.lesson.model.MatchingRequest
import com.ssing.data.lesson.model.Participant
import com.ssing.data.lesson.remote.datasource.api.LessonDataSource
import com.ssing.data.lesson.remote.dto.request.LessonRequest
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailBeforeResponse
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailCanceledResponse
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailCompletedResponse
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailOngoingResponse
import com.ssing.data.lesson.remote.dto.response.InstructorLessonDetailResponse
import com.ssing.data.lesson.repository.api.LessonRepository
import javax.inject.Inject


internal class LessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonDataSource,
) : LessonRepository {

    override suspend fun lessonStart(lessonId: Long): Result<Unit> =
        apiResponseHandler.safeApiCall {
            dataSource.lesson(
                lessonId = lessonId,
                request = LessonRequest(lessonId = lessonId),
            )
        }.map { }
}

internal class InstructorLessonRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val dataSource: LessonDataSource,
) : InstructorLessonRepository {

    override suspend fun instructorLessonDetail(lessonId: Long): Result<InstructorLessonDetailRequestResult> =
        apiResponseHandler.safeApiCall {
            dataSource.instructorLessonDetail(lessonId = lessonId)
        }.map { it.toModel() }

    private fun InstructorLessonDetailResponse.toModel(): InstructorLessonDetailRequestResult = when (this) {
        is InstructorLessonDetailBeforeResponse -> InstructorLessonDetailBefore(
            lessonId = lessonId,
            confirmedCount = statusInfo.confirmedCount,
            requiredCount = statusInfo.requiredCount,
            currentActorConfirmed = statusInfo.currentActorConfirmed,
            instructorConfirmed = statusInfo.instructorConfirmed,
            representativeConsumerNames = lessonInfo.representativeConsumerNames,
            totalHeadcount = lessonInfo.totalHeadcount,
            resortDisplayName = lessonInfo.resort.displayName,
            sport = lessonInfo.sport,
            lessonLevel = lessonInfo.lessonLevel,
            scheduledAt = lessonInfo.scheduledAt,
            scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
            totalLessonPrice = lessonInfo.totalLessonPrice,
            matchingRequests = matchingRequests.map {
                MatchingRequest(
                    matchingRequestId = it.matchingRequestId,
                    representativeMemberId = it.representativeMemberId,
                    representativeMemberName = it.representativeMemberName,
                    headcount = it.headcount,
                    teamLessonPrice = it.teamLessonPrice,
                    startConfirmed = it.startConfirmed,
                    participants = it.participants.map { p ->
                        Participant(p.participantId, p.gender, p.age)
                    },
                )
            },
        )

        is InstructorLessonDetailOngoingResponse -> InstructorLessonDetailOngoing(
            lessonId = lessonId,
            serverTime = statusInfo.serverTime,
            actualStartedAt = statusInfo.actualStartedAt,
            expectedEndedAt = statusInfo.expectedEndedAt,
            elapsedSeconds = statusInfo.elapsedSeconds,
            remainingSeconds = statusInfo.remainingSeconds,
            representativeConsumerNames = lessonInfo.representativeConsumerNames,
            totalHeadcount = lessonInfo.totalHeadcount,
            resortDisplayName = lessonInfo.resort.displayName,
            sport = lessonInfo.sport,
            lessonLevel = lessonInfo.lessonLevel,
            scheduledAt = lessonInfo.scheduledAt,
            scheduledDurationMinutes = lessonInfo.scheduledDurationMinutes,
            totalLessonPrice = lessonInfo.totalLessonPrice,
            matchingRequests = matchingRequests.map {
                MatchingRequest(
                    matchingRequestId = it.matchingRequestId,
                    representativeMemberId = it.representativeMemberId,
                    representativeMemberName = it.representativeMemberName,
                    headcount = it.headcount,
                    teamLessonPrice = it.teamLessonPrice,
                    startConfirmed = null,
                    participants = it.participants.map { p ->
                        Participant(p.participantId, p.gender, p.age)
                    },
                )
            },
        )

        is InstructorLessonDetailCompletedResponse -> InstructorLessonDetailCompleted(
            lessonId = lessonId,
            representativeConsumerNames = lessonInfo.representativeConsumerNames,
            totalHeadcount = lessonInfo.totalHeadcount,
            resortDisplayName = lessonInfo.resort.displayName,
            sport = lessonInfo.sport,
            lessonLevel = lessonInfo.lessonLevel,
            lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
            actualStartedAt = lessonInfo.actualStartedAt,
            actualEndedAt = lessonInfo.actualEndedAt,
            actualDurationMinutes = lessonInfo.actualDurationMinutes,
            totalLessonPrice = lessonInfo.totalLessonPrice,
            matchingRequests = matchingRequests.map {
                MatchingRequest(
                    matchingRequestId = it.matchingRequestId,
                    representativeMemberId = it.representativeMemberId,
                    representativeMemberName = it.representativeMemberName,
                    headcount = it.headcount,
                    teamLessonPrice = it.teamLessonPrice,
                    startConfirmed = null,
                    participants = it.participants.map { p ->
                        Participant(p.participantId, p.gender, p.age)
                    },
                )
            },
        )

        is InstructorLessonDetailCanceledResponse -> InstructorLessonDetailCanceled(
            lessonId = lessonId,
            canceledAt = cancelInfo.canceledAt,
            canceledByName = cancelInfo.canceledBy.name,
            cancelReason = cancelInfo.cancelReason,
            representativeConsumerNames = lessonInfo.representativeConsumerNames,
            totalHeadcount = lessonInfo.totalHeadcount,
            resortDisplayName = lessonInfo.resort.displayName,
            sport = lessonInfo.sport,
            lessonLevel = lessonInfo.lessonLevel,
            lessonDurationMinutes = lessonInfo.lessonDurationMinutes,
            totalLessonPrice = lessonInfo.totalLessonPrice,
            matchingRequests = matchingRequests.map {
                MatchingRequest(
                    matchingRequestId = it.matchingRequestId,
                    representativeMemberId = it.representativeMemberId,
                    representativeMemberName = it.representativeMemberName,
                    headcount = it.headcount,
                    teamLessonPrice = it.teamLessonPrice,
                    startConfirmed = null,
                    participants = it.participants.map { p ->
                        Participant(p.participantId, p.gender, p.age)
                    },
                )
            },
        )
    }
}