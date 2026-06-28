package com.ssing.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * UiEffect를 Lifecycle에 맞춰 안전하게 처리합니다.
 *
 * [LaunchedEffect]만 사용할 경우 백그라운드 진입 시에도 effect가 처리될 수 있어,
 * [repeatOnLifecycle]로 감싸 포그라운드 복귀 시점에만 수집되도록 보장합니다.
 *
 * @param effectFlow 수집할 effect Flow
 * @param lifecycleState 수집을 시작할 Lifecycle 상태 (기본: STARTED)
 * @param onEffect effect 처리 콜백
 */
@Composable
fun <T> HandleUiEffects(
    effectFlow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onEffect: suspend (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            effectFlow.collect { effect ->
                launch { onEffect(effect) }
            }
        }
    }
}