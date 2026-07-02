package com.ssing.data.dummy.remote.datasource.impl

import com.ssing.core.network.model.BaseResponse
import com.ssing.data.dummy.remote.datasource.api.DummyDataSource
import com.ssing.data.dummy.remote.dto.GetInstructorResponse
import javax.inject.Inject

class DummyDataSourceImpl @Inject constructor() : DummyDataSource {

    override suspend fun getInstructorList(): BaseResponse<List<GetInstructorResponse>> {
        return BaseResponse(
            success = true,
            code = "",
            message = "",
            data = listOf(
                GetInstructorResponse(
                    id = 1L,
                    name = "김강사",
                    rating = 4.9f,
                    career = "10년",
                    sport = "스키",
                    level = "중급",
                    resort = "하이원리조트",
                ),
                GetInstructorResponse(
                    id = 2L,
                    name = "이강사",
                    rating = 4.7f,
                    career = "5년",
                    sport = "보드",
                    level = "초급",
                    resort = "비발디파크",
                ),
            ),
        )
    }

    override suspend fun postLogin() {}
}
