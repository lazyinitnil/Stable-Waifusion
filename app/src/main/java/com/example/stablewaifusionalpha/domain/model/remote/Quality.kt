package com.example.stablewaifusionalpha.domain.model.remote

data class Quality(
    val name: String,
    val styles: List<Style>,
    val resolutions: List<ResolutionLevel>,
    val imageFormats: List<ImageFormat>
)


