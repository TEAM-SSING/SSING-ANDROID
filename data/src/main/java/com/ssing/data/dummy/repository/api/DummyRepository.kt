package com.ssing.data.dummy.repository.api

import com.ssing.data.dummy.model.DummyInstructor

interface DummyRepository {
    suspend fun fetchInstructorList(): Result<List<DummyInstructor>>

    suspend fun login(): Result<Unit>
}
