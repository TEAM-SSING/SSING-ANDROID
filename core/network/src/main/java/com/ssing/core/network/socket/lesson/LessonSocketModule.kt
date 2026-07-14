package com.ssing.core.network.socket.lesson

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
    abstract fun bindLessonSocketManager(
        impl: LessonSocketManager,
    ): SocketManager<LessonEnvelope<JsonElement>>
}
