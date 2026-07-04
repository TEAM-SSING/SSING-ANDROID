package com.ssing.core.network.token

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import com.ssing.core.network.dto.request.TokenRefreshRequest
import com.ssing.core.network.service.ReissueService
import com.ssing.core.network.session.AuthSessionManager
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Access Token 재발급 로직을 한 곳에서 관리.
 * TokenAuthenticator(HTTP)와 BaseSocketManager(WebSocket)가 공용으로 사용한다.
 *
 * - 동시 재발급 방지 및 토큰 쓰기는 [TokenAccessManager]의 Mutex에 위임
 * - Refresh Token까지 만료(401)되면 토큰 삭제 + 세션 만료 이벤트 발행
 */
@Singleton
internal class TokenReissueManager @Inject constructor(
    private val reissueService: ReissueService,
    private val tokenAccessManager: TokenAccessManager,
    private val authSessionManager: AuthSessionManager,
) {
    /**
     * 새 Access Token을 반환한다. 재발급 실패 시 null.
     *
     * @param failedAccessToken 401을 받은 요청에 사용됐던 Access Token.
     * 저장된 토큰과 다르면 다른 요청이 이미 재발급을 끝낸 것이므로 API 호출 없이 저장된 토큰을 반환한다.
     */
    suspend fun reissue(failedAccessToken: String?): String? = tokenAccessManager.withLock {
        val storedToken = getAccessToken()
        if (failedAccessToken != null && storedToken != null && storedToken != failedAccessToken) {
            Timber.d("다른 요청이 이미 토큰을 갱신함 - 재발급 생략")
            return@withLock storedToken
        }

        val refreshToken = getRefreshToken()
            ?: return@withLock expireSession(this)

        try {
            val response = reissueService.postRefresh(TokenRefreshRequest(refreshToken))
            val newAccessToken = response.data.accessToken

            setAccessToken(newAccessToken)
            Timber.d("Access Token 재발급 성공")
            newAccessToken
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Timber.w("Refresh Token 만료 - 세션 종료")
                expireSession(this)
            } else {
                Timber.e(e, "토큰 재발급 실패 (HTTP ${e.code()})")
                null
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e, "토큰 재발급 중 예외 발생")
            null
        }
    }

    private suspend fun expireSession(tokenDataSource: LocalTokenDataSource): String? {
        tokenDataSource.clearTokens()
        authSessionManager.notifySessionExpired()
        return null
    }
}
