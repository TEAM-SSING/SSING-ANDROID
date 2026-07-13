package com.ssing.core.network.socket.matching

import com.ssing.core.network.session.AuthSessionManager
import com.ssing.core.network.socket.BaseSocketManager
import com.ssing.core.network.token.TokenAccessManager
import com.ssing.core.network.token.TokenReissueManager
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.hildan.krossbow.stomp.StompClient
import javax.inject.Inject

internal class MatchingSocketManager @Inject constructor(
    client: StompClient,
    tokenAccessManager: TokenAccessManager,
    tokenReissueManager: TokenReissueManager,
    authSessionManager: AuthSessionManager,
    json: Json,
) : BaseSocketManager<MatchingEnvelope<JsonElement>>(
    ioDispatcher = Dispatchers.IO,
    client = client,
    tokenAccessManager = tokenAccessManager,
    tokenReissueManager = tokenReissueManager,
    authSessionManager = authSessionManager,
    json = json,
    serializer = MatchingEnvelope.serializer(JsonElement.serializer()),
) {
    override val destination: String = "/user/queue/matching"
}
