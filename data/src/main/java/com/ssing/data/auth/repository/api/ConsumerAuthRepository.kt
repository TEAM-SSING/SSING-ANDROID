package com.ssing.data.auth.repository.api

interface ConsumerAuthRepository {
    suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): Result<Unit>
}