package com.ssing.data.auth.repository.api

interface StartConfirmationRepository {
    suspend fun startConfirmation(): Result<Unit>
}