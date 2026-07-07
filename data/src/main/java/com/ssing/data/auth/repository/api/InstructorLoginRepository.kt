package com.ssing.data.auth.repository.api

import com.ssing.data.auth.model.InstructorLoginResult

interface InstructorLoginRepository {
    suspend fun loginWithKakao(kakaoAccessToken: String): Result<InstructorLoginResult>
}
