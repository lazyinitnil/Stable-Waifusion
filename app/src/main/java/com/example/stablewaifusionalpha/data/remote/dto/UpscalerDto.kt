package com.example.stablewaifusionalpha.data.remote.dto

data class UpscalerDto(
    val model_name: String,
    val model_path: String,
    val model_url: String,
    val name: String,
    val scale: Int
)
