package com.ssing.data.auth.repository.api

interface InstructorLoginRepository {
    suspend fun loginWithKakao(kakaoAccessToken: String): Result<Unit>
}
