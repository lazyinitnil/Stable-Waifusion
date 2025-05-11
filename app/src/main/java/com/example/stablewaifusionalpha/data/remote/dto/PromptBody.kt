package com.example.stablewaifusionalpha.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PromptBody(
    val prompt: Map<String, Any>,
    @SerializedName("client_id") val clientId: String
)

data class PromptResponseDto(
    @SerializedName("prompt_id") val promptId: String
)

data class HistoryResponseDto(
    val outputs: Map<String, OutputData>
)

data class OutputData(
    val images: List<ImageData>
)

data class ImageData(
    val filename: String,
    val subfolder: String,
    val type: String
)
