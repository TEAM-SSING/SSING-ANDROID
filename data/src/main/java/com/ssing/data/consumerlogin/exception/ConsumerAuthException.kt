package com.ssing.data.consumerlogin.exception

import com.ssing.core.network.exception.BusinessException

sealed class ConsumerAuthException(
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
    ) : ConsumerAuthException(serverCode, message, requestId)

    class AuthInvalidKakaoToken(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) : ConsumerAuthException(serverCode, message, requestId)

    class ExternalServiceUnavailable(
        serverCode: String?,
        message: String?,
        requestId: String?,
    ) : ConsumerAuthException(serverCode, message, requestId)
}