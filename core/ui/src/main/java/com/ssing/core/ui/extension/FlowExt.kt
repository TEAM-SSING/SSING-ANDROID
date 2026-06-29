package com.ssing.core.ui.extension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Cold Flow를 Hot StateFlow로 변환합니다.
 *
 * [SharingStarted.WhileSubscribed] 전략을 사용하여 구독자가 있을 때만 활성화되며,
 * 마지막 구독자가 사라진 후 [timeout]만큼 대기한 뒤 업스트림을 취소합니다.
 *
 * timeout 기본값(5000ms)은 Configuration change 등의 짧은 중단 시
 * 불필요한 재시작을 방지하고 Main 스레드에서 UI 업데이트를 원활하게 합니다.
 *
 * @param scope StateFlow가 동작할 CoroutineScope
 * @param initialValue StateFlow의 초기값
 * @param timeout 구독 해제 후 업스트림 취소까지의 대기 시간 (기본: 5000ms)
 * @return Hot StateFlow
 */
fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialValue: T,
    timeout: Long = 5_000L,
): StateFlow<T> =
    this.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(timeout),
        initialValue = initialValue,
    )
