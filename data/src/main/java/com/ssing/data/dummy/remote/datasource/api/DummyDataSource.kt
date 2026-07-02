package com.ssing.data.dummy.remote.datasource.api

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.dummy.remote.dto.GetInstructorResponse

interface DummyDataSource {
    suspend fun getInstructorList(): BaseResponse<List<GetInstructorResponse>>

    suspend fun postLogin()
}
