package com.ssing.core.network.exception

abstract class AppException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : ApiException(serverCode, message, requestId)
