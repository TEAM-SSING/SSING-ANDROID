package com.ssing.data.auth.repository.api

interface AuthRepository {
    suspend fun postConsumerKakaoAuth(kakaoAccessToken: String): Result<Unit>
    suspend fun postInstructorKakaoAuth(kakaoAccessToken: String): Result<Unit>
    suspend fun postLogout(): Result<Unit>
}