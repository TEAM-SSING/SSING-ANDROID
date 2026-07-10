package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartConfirmationResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("lessonId")
    val lessonId: Data,
    @SerialName("lessonStatus")
    val lessonStatus: Data,
    @SerialName("currentActorConfirmed")
    val currentActorConfirmed: Data,
    @SerialName("confirmedCount")
    val confirmedCount: Data,
    @SerialName("requiredCount")
    val requiredCount: Data,
    @SerialName("startedAt")
    val startedAt: Data
) {

    @Serializable
    data class Data(
        @SerialName("lessonId")
        val lessonId: Long,
        @SerialName("lessonStatus")
        val lessonStatus: String,
        @SerialName("currentActorConfirmed")
        val currentActorConfirmed: Boolean,
        @SerialName("confirmedCount")
        val confirmedCount: Int,
        @SerialName("requiredCount")
        val requiredCount: Int,
        @SerialName("startedAt")
        val startedAt: String,
    )
}