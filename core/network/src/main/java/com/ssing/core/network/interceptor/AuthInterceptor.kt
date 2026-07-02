package com.ssing.core.network.interceptor

import com.ssing.core.localstorage.datastore.LocalTokenDataSource
import com.ssing.core.network.constant.AUTHORIZATION
import com.ssing.core.network.constant.BEARER_PREFIX
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * 저장된 Access Token을 모든 요청의 Authorization 헤더에 자동으로 첨부한다.
 */
internal class AuthInterceptor @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenDataSource.getAccessToken() }
            ?: return chain.proceed(chain.request())

        val request = chain.request().newBuilder()
            .header(AUTHORIZATION, "$BEARER_PREFIX$accessToken")
            .build()

        return chain.proceed(request)
    }
}
