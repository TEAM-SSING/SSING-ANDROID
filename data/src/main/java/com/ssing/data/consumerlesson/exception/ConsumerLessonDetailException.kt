package com.ssing.data.consumerlesson.exception

import com.ssing.core.network.exception.BusinessException

sealed class ConsumerLessonDetailException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class Unauthenticated(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class AuthInvalidToken(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class AuthTokenExpired(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class Forbidden(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class LessonForbidden(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class LessonNotFound(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class LessonPriceNotFound(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class LessonCancellationNotFound(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class LessonInvalidState(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

    class InternalError(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

}