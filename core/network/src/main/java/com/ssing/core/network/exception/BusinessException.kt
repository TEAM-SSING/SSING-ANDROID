package com.ssing.core.network.exception

abstract class BusinessException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : ApiException(serverCode, message, requestId)
