package com.ssing.core.network.exception

sealed class ApiError(
    val serverCode: String?,
    serverMessage: String?,
    val requestId: String? = null,
) : Exception(serverMessage) {

    class BadRequest(serverCode: String?, serverMessage: String?, requestId: String?) :
        ApiError(serverCode, serverMessage, requestId)

    class Unauthorized(serverCode: String?, serverMessage: String?, requestId: String?) :
        ApiError(serverCode, serverMessage, requestId)

    class Forbidden(serverCode: String?, serverMessage: String?, requestId: String?) :
        ApiError(serverCode, serverMessage, requestId)

    class NotFound(serverCode: String?, serverMessage: String?, requestId: String?) :
        ApiError(serverCode, serverMessage, requestId)

    class Conflict(serverCode: String?, serverMessage: String?, requestId: String?) :
        ApiError(serverCode, serverMessage, requestId)

    class InternalServerError(
        serverCode: String?,
        serverMessage: String?,
        requestId: String?,
    ) :
        ApiError(serverCode, serverMessage, requestId)

    class Unknown : ApiError(null, "Unknown error")

    class NetworkConnection : ApiError(null, "Network connection failed")
}
