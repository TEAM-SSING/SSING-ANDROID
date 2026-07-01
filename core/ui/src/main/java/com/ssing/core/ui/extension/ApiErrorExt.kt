package com.ssing.core.ui.extension

import com.ssing.core.network.exception.ApiError

val ApiError.uiMessage: String
    get() = when (this) {
        is ApiError.BadRequest -> this.message ?: "잘못된 요청이에요."
        is ApiError.Conflict -> this.message ?: "요청한 정보를 찾을 수 없어요."
        is ApiError.Forbidden -> this.message ?: "접근 권한이 없어요."
        is ApiError.InternalServerError -> "알 수 없는 오류가 발생했어요."
        is ApiError.NetworkConnection -> "네트워크 연결이 불안정해요."
        is ApiError.NotFound -> this.message ?: "요청을 처리할 수 없어요."
        is ApiError.Unauthorized -> "로그인이 필요해요."
        is ApiError.Unknown -> "알 수 없는 오류가 발생했어요."
    }
