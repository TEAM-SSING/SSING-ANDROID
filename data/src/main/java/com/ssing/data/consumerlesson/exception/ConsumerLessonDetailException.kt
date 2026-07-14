package com.ssing.data.consumerlesson.exception

import com.ssing.core.network.exception.BusinessException

sealed class ConsumerLessonDetailException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class LessonInvalidState(serverCode: String?, message: String?, requestId: String?) :
        ConsumerLessonDetailException(serverCode, message, requestId)

}