package com.ssing.core.ui.extension

import com.ssing.core.network.exception.ApiException
import com.ssing.core.network.exception.AppException

val ApiException.uiMessage: String
    get() = when (this) {
        is AppException -> this.message ?: "알 수 없는 오류가 발생했어요."
        is ApiException.BadRequest -> this.message ?: "잘못된 요청이에요."
        is ApiException.Conflict -> this.message ?: "요청한 정보를 찾을 수 없어요."
        is ApiException.Forbidden -> this.message ?: "접근 권한이 없어요."
        is ApiException.InternalServerError -> "알 수 없는 오류가 발생했어요."
        is ApiException.NetworkConnection -> "네트워크 연결이 불안정해요."
        is ApiException.NotFound -> this.message ?: "요청을 처리할 수 없어요."
        is ApiException.Unauthorized -> "로그인이 필요해요."
        is ApiException.Unknown -> "알 수 없는 오류가 발생했어요."
    }
