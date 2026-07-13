package com.ssing.data.consumerhome.repository.api

import com.ssing.data.consumerhome.model.ConsumerHome

interface ConsumerHomeRepository{
    suspend fun getConsumerHome(): Result<ConsumerHome>
}