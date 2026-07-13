package com.ssing.core.network.socket.lesson

import com.ssing.core.network.model.BaseEnvelope
import com.ssing.core.network.socket.LessonSocket
import com.ssing.core.network.socket.SocketManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.JsonElement
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LessonSocketModule {

    @Binds
    @Singleton
    @LessonSocket
    abstract fun bindLessonSocketManager(
        impl: LessonSocketManager,
    ): SocketManager<BaseEnvelope<JsonElement>>
}
