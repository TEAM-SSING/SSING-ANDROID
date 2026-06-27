package com.ssing.data.dummy.remote.datasource.api

import com.ssing.data.dummy.remote.dto.GetInstructorResponse

interface DummyDataSource {
    suspend fun getInstructorList(): List<GetInstructorResponse>
}
