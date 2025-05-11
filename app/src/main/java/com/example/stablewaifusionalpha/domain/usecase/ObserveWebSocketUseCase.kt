package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketEvent
import com.example.stablewaifusionalpha.domain.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWebSocketUseCase @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

    fun observeGenerationEvents(promptId: String) : Flow<WebSocketEvent> {
        return webSocketRepository.observeWebSocket(promptId)
    }

}
