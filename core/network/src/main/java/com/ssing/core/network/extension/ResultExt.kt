package com.ssing.core.network.extension

import com.ssing.core.network.exception.ApiException

inline fun <T> Result<T>.mapApiException(
    transform: (ApiException) -> Throwable,
): Result<T> = recoverCatching { throwable ->
    throw if (throwable is ApiException) transform(throwable) else throwable
}
