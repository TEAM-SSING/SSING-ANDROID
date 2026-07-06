package com.ssing.data.consumerlogin.repository.api

interface ConsumerAuthRepository {
    suspend fun postConsumerKakaoAuth(
        kakaoAccessToken: String,
    ): Result<Unit>
}