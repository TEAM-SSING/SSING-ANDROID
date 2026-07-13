package com.ssing.data.lesson.remote.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = InstructorLessonDetailResponseSerializer::class)
internal sealed interface InstructorLessonDetailResponse

internal object InstructorLessonDetailResponseSerializer :
    JsonContentPolymorphicSerializer<InstructorLessonDetailResponse>(
        InstructorLessonDetailResponse::class
    ) {
    override fun selectDeserializer(element: JsonElement) =
        when (element.jsonObject["lessonStatus"]?.jsonPrimitive?.content) {
            "CONFIRMED" -> InstructorLessonDetailBeforeResponse.serializer()
            "IN_PROGRESS" -> InstructorLessonDetailOngoingResponse.serializer()
            "COMPLETED" -> InstructorLessonDetailCompletedResponse.serializer()
            "CANCELED" -> InstructorLessonDetailCanceledResponse.serializer()
            else -> error("Unknown lessonStatus: ${element.jsonObject["lessonStatus"]}")
        }
}