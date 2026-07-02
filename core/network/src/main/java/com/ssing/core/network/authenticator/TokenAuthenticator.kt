package com.ssing.core.network.authenticator

import com.ssing.core.network.constant.AUTHORIZATION
import com.ssing.core.network.constant.BEARER_PREFIX
import com.ssing.core.network.token.TokenReissueManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * 401 응답 시 Access Token을 재발급받아 원래 요청을 재시도한다.
 *
 * 실제 재발급은 [TokenReissueManager]에 위임하고,
 * 여기서는 재시도 횟수 제한과 요청 헤더 교체만 담당한다.
 */
internal class TokenAuthenticator @Inject constructor(
    private val tokenReissueManager: TokenReissueManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.retryCount() >= MAX_RETRY_COUNT) return null

        val failedAccessToken = response.request.header(AUTHORIZATION)
            ?.removePrefix(BEARER_PREFIX)

        val newAccessToken = runBlocking {
            tokenReissueManager.reissue(failedAccessToken)
        } ?: return null

        return response.request.newBuilder()
            .header(AUTHORIZATION, "$BEARER_PREFIX$newAccessToken")
            .build()
    }

    private fun Response.retryCount(): Int {
        var count = 0
        var prior = priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    private companion object {
        const val MAX_RETRY_COUNT = 2
    }
}
