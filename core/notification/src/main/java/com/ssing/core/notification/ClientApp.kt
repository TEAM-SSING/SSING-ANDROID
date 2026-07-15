package com.ssing.core.notification

/**
 * FCM 토큰 등록 시 서버에 전달하는 요청 앱 유형.
 *
 * core:notification 은 consumer/instructor 앱 공용 모듈이므로
 * 실제 값은 각 앱 모듈의 Hilt 모듈에서 주입한다.
 */
enum class ClientApp {
    CONSUMER,
    INSTRUCTOR,
}
