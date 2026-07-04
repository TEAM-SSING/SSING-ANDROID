package com.ssing.core.network.token

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 토큰 읽기/쓰기의 단일 진입점.
 *
 * 쓰기 작업은 모두 [withLock]을 통해 하나의 [Mutex] 안에서 직렬화된다.
 * [TokenReissueManager]와 [BaseSocketManager]가 각자 [LocalTokenDataSource]를
 * 직접 수정하면 Mutex 보호를 받지 못하므로, 모든 수정을 여기에 위임한다.
 */
@Singleton
class TokenAccessManager @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
) {
    private val mutex = Mutex()

    suspend fun getAccessToken(): String? = tokenDataSource.getAccessToken()

    suspend fun <T> withLock(block: suspend LocalTokenDataSource.() -> T): T =
        mutex.withLock { tokenDataSource.block() }
}
