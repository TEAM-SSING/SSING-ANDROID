package com.ssing.data.auth.exception

import com.ssing.core.network.exception.BusinessException

sealed class AuthException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class ValidationFailed(serverCode: String?, message: String?, requestId: String?) :
        AuthException(serverCode, message, requestId)

    class InvalidKakaoToken(serverCode: String?, message: String?, requestId: String?) :
        AuthException(serverCode, message, requestId)

    class ExternalServiceUnavailable(serverCode: String?, message: String?, requestId: String?) :
        AuthException(serverCode, message, requestId)

    class InvalidToken(serverCode: String?, message: String?, requestId: String?) :
        AuthException(serverCode, message, requestId)
}