package com.ssing.core.network.socket.matching

import com.ssing.core.network.socket.SocketManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.JsonElement
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MatchingSocketModule {

    @Binds
    @Singleton
    abstract fun bindMatchingSocketManager(
        impl: MatchingSocketManager,
    ): SocketManager<MatchingEnvelope<JsonElement>>
}
