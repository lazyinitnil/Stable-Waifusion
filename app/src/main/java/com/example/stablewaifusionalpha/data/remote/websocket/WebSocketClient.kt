package com.example.stablewaifusionalpha.data.remote.websocket

import android.graphics.BitmapFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

class WebSocketClient @Inject constructor(
    private val client: OkHttpClient,
    @Named("base_url") private val baseUrl: String,
) {
    private var webSocket: WebSocket? = null
    private var _wsFlow = MutableSharedFlow<WebSocketEvent>(extraBufferCapacity = 64)

    fun observeWebSocket(
        clientId: String,
        promptId: String
    ): Flow<WebSocketEvent>{
        webSocket?.close(1000, "Reconnecting")

        _wsFlow = MutableSharedFlow(extraBufferCapacity = 64)
        webSocket = null
        val wsUrl = "${baseUrl.replace("http", "ws")}/ws?clientId=$clientId"
        val request = Request.Builder().url(wsUrl).build()

        if (webSocket == null) {
            webSocket = client.newWebSocket(request, object : WebSocketListener() {

                override fun onMessage(webSocket: WebSocket, text: String) {
                    val json = JSONObject(text)
                    val type = json.getString("type")

                    when (type) {
                        "progress" -> {
                            val data = json.getJSONObject("data")

                            val value = data.getInt("value")
                            val max = data.getInt("max")
                            val percent = if (max != 0) (value * 100 / max) else 0
                           /* val node = data.getString("node")
                            val promptId = data.getString("prompt_id")*/

                            _wsFlow.tryEmit(WebSocketEvent.Progress(percent))
                        }

                        "executing" ->  {
                            val data = json.getJSONObject("data")
                            if (data.isNull("node") && data.getString("prompt_id") == promptId) {
                                _wsFlow.tryEmit(WebSocketEvent.Done)
                            }
                        }
                    }

                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    val byteArray = bytes.toByteArray()
                    if (byteArray.size <= 8) return

                    try {
                       /* val imageIndex = ByteBuffer.wrap(byteArray, 0, 4).order(ByteOrder.LITTLE_ENDIAN).int*/
                        val imageData = byteArray.copyOfRange(8, byteArray.size)
                        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                        if (bitmap != null) {
                            _wsFlow.tryEmit(WebSocketEvent.LivePreview(imageData))
                        }
                    } catch (e: Exception) {
                        _wsFlow.tryEmit(WebSocketEvent.Error("Error decoding image: ${e.localizedMessage}"))
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    _wsFlow.tryEmit(WebSocketEvent.Error(t.localizedMessage ?: "WebSocket error"))
                }
            })
        }

        return _wsFlow.asSharedFlow()
    }
}
