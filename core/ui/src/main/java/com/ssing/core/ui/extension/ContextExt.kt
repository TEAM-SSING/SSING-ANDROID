package com.ssing.core.ui.extension

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

/**
 * 주어진 URL을 기본 브라우저로 엽니다.
 *
 * @param url URL 문자열
 * @return 성공 시 true, 실패 시 false
 */
fun Context.openUrl(url: String?): Boolean {
    if (url == null) return false
    return runCatching {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }.isSuccess
}


/**
 * 짧은 길이의 토스트 메시지를 표시합니다.
 *
 * @param message 표시할 메시지
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
