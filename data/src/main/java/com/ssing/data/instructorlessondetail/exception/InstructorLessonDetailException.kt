package com.ssing.data.instructorlessondetail.exception

import com.ssing.core.network.exception.BusinessException

sealed class InstructorLessonDetailException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class Unauthorized(serverCode: String?, message: String?, requestId: String?) :
        InstructorLessonDetailException(serverCode, message, requestId)

    class Forbidden(serverCode: String?, message: String?, requestId: String?) :
        InstructorLessonDetailException(serverCode, message, requestId)

    class LessonNotFound(serverCode: String?, message: String?, requestId: String?) :
        InstructorLessonDetailException(serverCode, message, requestId)

    class LessonConflict(serverCode: String?, message: String?, requestId: String?) :
        InstructorLessonDetailException(serverCode, message, requestId)

    class InternalError(serverCode: String?, message: String?, requestId: String?) :
        InstructorLessonDetailException(serverCode, message, requestId)
}