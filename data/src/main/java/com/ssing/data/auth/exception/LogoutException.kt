package com.ssing.data.auth.exception

import com.ssing.core.network.exception.BusinessException

sealed class LogoutException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class ValidationFailed(serverCode: String?, message: String?, requestId: String?) :
        LogoutException(serverCode, message, requestId)

    class InvalidToken(serverCode: String?, message: String?, requestId: String?) :
        LogoutException(serverCode, message, requestId)
}
