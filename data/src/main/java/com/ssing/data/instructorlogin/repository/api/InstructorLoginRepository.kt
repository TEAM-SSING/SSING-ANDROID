package com.ssing.data.instructorlogin.repository.api

import com.ssing.data.instructorlogin.model.InstructorLoginResult

interface InstructorLoginRepository {
    suspend fun loginWithKakao(kakaoAccessToken: String): Result<InstructorLoginResult>
}
