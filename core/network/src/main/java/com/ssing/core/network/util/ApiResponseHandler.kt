package com.ssing.core.network.util

import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.model.BaseResponse
import com.ssing.core.network.model.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * API 호출 결과를 [Result]로 감싸고, 실패 시 발생한 예외를 [ApiException]으로 변환합니다.
 *
 * 레포지토리는 이 핸들러를 통해 API를 호출함으로써, Result의 실패값이 항상
 * [ApiException]임을 보장받을 수 있습니다.
 */
@Singleton
class ApiResponseHandler @Inject constructor(
    private val json: Json,
) {

    /**
     * [BaseResponse]를 반환하는 API를 호출하고 [BaseResponse.data]만 추출해 [Result]로 반환합니다.
     *
     * @param block 실행할 suspend API 호출
     */
    suspend fun <T> safeApiCall(
        block: suspend () -> BaseResponse<T>,
    ): Result<T> =
        suspendRunCatching {
            block().data
        }.recoverCatching { throwable ->
            Timber.e(
                throwable,
                "safeApiCall 원본 예외: type=${throwable::class.qualifiedName}, message=${throwable.message}"
            )

            throw mapThrowable(throwable)
        }

    /**
     * 반환값이 없는(Unit) API를 호출하고 결과를 [Result]로 반환합니다.
     *
     * @param block 실행할 suspend API 호출
     */
    suspend fun safeUnitApiCall(block: suspend () -> Unit): Result<Unit> =
        suspendRunCatching {
            block()
        }.recoverCatching { throwable ->
            throw mapThrowable(throwable)
        }

    private fun mapThrowable(throwable: Throwable): Throwable =
        when (throwable) {
            is HttpException -> parseHttpException(throwable)
            is UnknownHostException, is ConnectException, is SocketTimeoutException -> ApiException.NetworkConnection()
            is ApiException -> throwable
            else -> {
                Timber.e(throwable, "ApiResponseHandler에서 처리 불가능한 Throwable 발생")
                ApiException.Unknown()
            }
        }

    private fun parseHttpException(e: HttpException): ApiException {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = runCatching {
            errorBody?.let { json.decodeFromString<ErrorResponse>(it) }
        }.onFailure { throwable ->
            Timber.e(throwable, "ErrorBody 파싱 중 에러 발생: $errorBody")
        }.getOrNull()

        val code = errorResponse?.code
        val message = errorResponse?.message
        val requestId = errorResponse?.requestId

        return when (e.code()) {
            400 -> ApiException.BadRequest(code, message, requestId)
            401 -> ApiException.Unauthorized(code, message, requestId)
            403 -> ApiException.Forbidden(code, message, requestId)
            404 -> ApiException.NotFound(code, message, requestId)
            409 -> ApiException.Conflict(code, message, requestId)
            in 500..599 -> ApiException.InternalServerError(code, message, requestId)
            else -> ApiException.Unknown()
        }
    }
}
