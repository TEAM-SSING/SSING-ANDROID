package com.ssing.data.lesson.repository.api

interface StartConfirmationRepository {
    suspend fun startConfirmation(): Result<Unit>
}