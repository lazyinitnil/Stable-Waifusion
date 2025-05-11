package com.example.stablewaifusionalpha.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Node(
    val inputs: Map<String, JsonElement>? = null,
    val class_type: String? = null,
    @SerialName("_meta")
    val meta: Map<String, JsonElement>? = null
)
