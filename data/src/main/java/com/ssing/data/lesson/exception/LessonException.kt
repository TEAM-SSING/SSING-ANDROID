package com.ssing.data.lesson.exception

import com.ssing.core.network.exception.BusinessException

sealed class LessonException(
    serverCode: String,
    message: String,
    requestId: String,
) : BusinessException(serverCode, message, requestId)