package com.ssing.data.lesson.exception

import com.ssing.core.network.exception.BusinessException

sealed class StartConfirmationException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class Unauthenticated(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class AuthInvalidToken(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class AuthTokenExpired(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class Forbidden(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class LessonNotFound(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class LessonStartNotAllowed(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class LessonInvalidState(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)

    class InternalError(serverCode: String?, message: String?, requestId: String?) :
        StartConfirmationException(serverCode, message, requestId)
}