package com.ssing.core.network.util

import com.ssing.core.network.exception.ApiError
import com.ssing.core.network.model.BaseResponse
import com.ssing.core.network.model.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiResponseHandler @Inject constructor(
    private val json: Json,
) {
    suspend fun <T> safeApiCall(block: suspend () -> BaseResponse<T>): Result<T> =
        suspendRunCatching {
            block().data
        }.recoverCatching { throwable ->
            throw mapThrowable(throwable)
        }

    suspend fun safeUnitApiCall(block: suspend () -> Unit): Result<Unit> =
        suspendRunCatching {
            block()
        }.recoverCatching { throwable ->
            throw mapThrowable(throwable)
        }

    private fun mapThrowable(throwable: Throwable): Throwable =
        when (throwable) {
            is HttpException -> parseHttpException(throwable)
            is UnknownHostException, is ConnectException, is SocketTimeoutException -> ApiError.NetworkConnection()
            is ApiError -> throwable
            else -> ApiError.Unknown()
        }

    private fun parseHttpException(e: HttpException): ApiError {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = runCatching {
            errorBody?.let { json.decodeFromString<ErrorResponse>(it) }
        }.getOrNull()

        val code = errorResponse?.code
        val message = errorResponse?.message
        val requestId = errorResponse?.requestId

        return when (e.code()) {
            400 -> ApiError.BadRequest(code, message, requestId)
            401 -> ApiError.Unauthorized(code, message, requestId)
            403 -> ApiError.Forbidden(code, message, requestId)
            404 -> ApiError.NotFound(code, message, requestId)
            409 -> ApiError.Conflict(code, message, requestId)
            in 500..599 -> ApiError.InternalServerError(code, message, requestId)
            else -> ApiError.Unknown()
        }
    }
}
