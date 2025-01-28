package com.example.stablewaifusionalpha.domain.model.remote

data class Style(
    val name: String,
    val modelPath: String,
    val preview: Int,
    val subStyles: List<SubStyle> = emptyList(),
    val stylePrompt: String? = null,
    val styleNegativePrompt: String? = null,
    val styleCfg: Float? = null,
    val styleSteps: Int? = null,
    val styleSampler: String? = null,
    val styleScheduler: String? = null,
    val styleClipSkip: Int? = null,
    val styleSDVae: String? = null,
    val styleEta: Int? = null
)