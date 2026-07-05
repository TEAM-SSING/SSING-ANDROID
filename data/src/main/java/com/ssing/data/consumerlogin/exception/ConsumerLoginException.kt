package com.ssing.data.consumerlogin.exception

import com.ssing.core.network.exception.BusinessException

sealed class ConsumerLoginException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(
    serverCode = serverCode,
    message = message,
    requestId = requestId,
) {

    class ValidationFailed(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) : ConsumerLoginException(serverCode, message, requestId)

    class AuthInvalidKakaoToken(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) : ConsumerLoginException(serverCode, message, requestId)

    class ExternalServiceUnavailable(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) : ConsumerLoginException(serverCode, message, requestId)
}