package com.ssing.data.home.repository.api

import com.ssing.data.home.model.ConsumerHomeSummary
import com.ssing.data.home.model.InstructorHomeSummary

interface HomeRepository{
    suspend fun getConsumerHome(): Result<ConsumerHomeSummary>
    suspend fun getInstructorHome(): Result<InstructorHomeSummary>
}
