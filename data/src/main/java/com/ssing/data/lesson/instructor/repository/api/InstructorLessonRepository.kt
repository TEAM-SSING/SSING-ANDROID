package com.ssing.data.lesson.instructor.repository.api

import com.ssing.core.network.socket.SocketState
import com.ssing.data.lesson.instructor.model.InstructorLessonDetail
import com.ssing.data.lesson.instructor.model.InstructorLessonSocketEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InstructorLessonRepository {
    val socketEvents: Flow<InstructorLessonSocketEvent>
    val socketState: StateFlow<SocketState>
    fun connectSocket()
    suspend fun disconnectSocket()

    suspend fun fetchInstructorLessonDetail(lessonId: Long): Result<InstructorLessonDetail>
}