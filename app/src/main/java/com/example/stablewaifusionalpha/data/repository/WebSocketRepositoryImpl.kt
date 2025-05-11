package com.example.stablewaifusionalpha.data.repository

import com.example.stablewaifusionalpha.core.common.ClientIdProvider
import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketClient
import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketEvent
import com.example.stablewaifusionalpha.domain.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val clientIdProvider: ClientIdProvider
): WebSocketRepository{

    override fun observeWebSocket(promptId: String): Flow<WebSocketEvent> {
        return webSocketClient.observeWebSocket(clientIdProvider.clientId, promptId)
    }

}