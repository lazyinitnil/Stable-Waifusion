package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketEvent
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {

    fun observeWebSocket(promptId: String): Flow<WebSocketEvent>

}
