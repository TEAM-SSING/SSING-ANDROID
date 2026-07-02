package com.ssing.core.network.util

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.cancellation.CancellationException

/**
 * suspend 함수를 안전하게 실행하고 결과를 [Result]로 반환합니다.
 *
 * [runCatching]과 달리 [CancellationException]을 올바르게 처리하여
 * 코루틴 취소가 전파되도록 합니다. [TimeoutCancellationException]은
 * 실패로 간주하여 [Result.failure]로 반환합니다.
 *
 * @param block 실행할 suspend 블록
 * @return 성공 시 [Result.success], 실패 시 [Result.failure]
 */
suspend inline fun <R> suspendRunCatching(crossinline block: suspend () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        Result.failure(e)
    }
