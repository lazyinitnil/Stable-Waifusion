package com.example.stablewaifusionalpha.data.remote.websocket

sealed class WebSocketEvent {
    data class Progress(val percent: Int, ) : WebSocketEvent()
    data class LivePreview( val imageData: ByteArray) : WebSocketEvent()
    data class Error(val message: String) : WebSocketEvent()
    data object Done : WebSocketEvent()
}
