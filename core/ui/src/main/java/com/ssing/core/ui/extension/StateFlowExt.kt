package com.ssing.core.ui.extension

import com.ssing.core.ui.state.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <T> MutableStateFlow<ApiState<T>>.updateSuccess(crossinline onUpdate: (T) -> T) {
    update { currentState ->
        if (currentState is ApiState.Success) {
            currentState.copy(data = onUpdate(currentState.data))
        } else {
            currentState
        }
    }
}
