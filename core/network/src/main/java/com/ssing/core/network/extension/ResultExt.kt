package com.ssing.core.network.extension

import com.ssing.core.network.exception.ApiError

inline fun <T> Result<T>.mapApiError(
    transform: (ApiError) -> Throwable,
): Result<T> = recoverCatching { throwable ->
    throw if (throwable is ApiError) transform(throwable) else throwable
}
