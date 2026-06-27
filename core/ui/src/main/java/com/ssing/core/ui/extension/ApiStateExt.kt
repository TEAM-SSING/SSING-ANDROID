package com.ssing.core.ui.extension

import com.ssing.core.ui.state.ApiState

inline fun <T> ApiState<T>.onSuccess(block: (T) -> Unit) {
    if (this is ApiState.Success) {
        block(data)
    }
}

fun <T> ApiState<T>.getSuccessDataOrNull(): T? {
    return (this as? ApiState.Success)?.data
}
