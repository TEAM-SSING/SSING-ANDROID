package com.ssing.data.home.repository.api

import com.ssing.data.home.model.ConsumerHome

interface HomeRepository{
    suspend fun getConsumerHome(): Result<ConsumerHome>
}