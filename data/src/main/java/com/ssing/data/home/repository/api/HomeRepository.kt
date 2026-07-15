package com.ssing.data.home.repository.api

import com.ssing.data.home.model.ConsumerHomeSummary

interface HomeRepository{
    suspend fun getConsumerHome(): Result<ConsumerHomeSummary>
}
