package com.ssing.core.network.exception

abstract class AppException(
    message: String?,
    val requestId: String?,
) : Exception(message)
