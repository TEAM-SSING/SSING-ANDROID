package com.ssing.core.network.extension

import com.ssing.core.network.exception.ApiException

/**
 * 실패한 [Result]가 [ApiException]인 경우, [transform]을 통해 API별로 필요한
 * 구체적인 예외(예: [com.ssing.core.network.exception.BusinessException] 하위 타입)로 다시 매핑합니다.
 *
 * 레포지토리에서 serverCode에 따라 분기 처리가 필요할 때 사용하며,
 * [ApiException]이 아닌 예외는 변형 없이 그대로 전파됩니다.
 *
 * @param transform 원본 [ApiException]을 받아 매핑할 예외를 반환하는 함수
 */
inline fun <T> Result<T>.mapApiException(
    transform: (ApiException) -> Throwable,
): Result<T> = recoverCatching { throwable ->
    throw if (throwable is ApiException) transform(throwable) else throwable
}
