package com.ssing.data.dummy.exception

import com.ssing.core.network.exception.AppException

sealed class LoginException(
    message: String?,
    requestId: String?,
) : AppException(message, requestId) {
    class BlockedUser(message: String?, requestId: String?) :
        LoginException(message, requestId) // 차단된 유저
}
