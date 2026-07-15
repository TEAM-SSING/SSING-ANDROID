package com.ssing.data.consumerlesson.remote.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = ConsumerLessonDetailResponseSerializer::class)
sealed interface ConsumerLessonDetailResponse

object ConsumerLessonDetailResponseSerializer : JsonContentPolymorphicSerializer<ConsumerLessonDetailResponse>(
    ConsumerLessonDetailResponse::class
) {
    override fun selectDeserializer(element: JsonElement) = when (element.jsonObject["lessonStatus"]?.jsonPrimitive?.content) {
        "CONFIRMED" -> ConsumerLessonDetailBeforeResponse.serializer()
        "IN_PROGRESS" -> ConsumerLessonDetailOngoingResponse.serializer()
        "COMPLETED" -> ConsumerLessonDetailCompletedResponse.serializer()
        "CANCELED" -> ConsumerLessonDetailCanceledResponse.serializer()
        else -> error("Unknown lessonStatus: ${element.jsonObject["lessonStatus"]}")
    }
}