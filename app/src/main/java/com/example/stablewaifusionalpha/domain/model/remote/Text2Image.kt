package com.example.stablewaifusionalpha.domain.model.remote

data class Text2Image (
    val prompt: String,
    val negativePrompt: String = "",
    val steps: Int = 24,
    val cfgScale: Float = 5.5f,
    val width: Int = 512,
    val height: Int = 512,
    val batchSize: Int = 1,
    val sampler: String = "Euler A",
    val scheduler: String = "Automatic"
)
