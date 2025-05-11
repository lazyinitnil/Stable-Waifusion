package com.example.stablewaifusionalpha.domain.model.remote

data class Style(
    val name: String,
    val preview: Int,
    val workflowPath: String,
    val styleParams: List<StyleParam> = emptyList(),
    val stylePrompt: String? = null
)

data class StyleParam(
    val title: String,
    val paramName: String,
    val paramValue: Any
)
