package com.ssing.data.dummy.exception

import com.ssing.core.network.exception.BusinessException

sealed class LoginException(
    serverCode: String?,
    message: String?,
    requestId: String?,
) : BusinessException(serverCode, message, requestId) {
    class BlockedUser(serverCode: String?, message: String?, requestId: String?) :
        LoginException(serverCode, message, requestId) // 차단된 유저
}
