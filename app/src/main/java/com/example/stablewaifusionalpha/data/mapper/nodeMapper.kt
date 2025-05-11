package com.example.stablewaifusionalpha.data.mapper

import com.example.stablewaifusionalpha.data.remote.dto.Node
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull


fun Node.toMap(): Map<String, Any> {
    return buildMap {
        inputs?.let { put("inputs", processInputs(it)) }
        class_type?.let { put("class_type", it) }
        meta?.let { put("_meta", processMeta(it)) }
    }
}

fun processInputs(inputs: Map<String, JsonElement>): Map<String, Any> {
    return inputs.mapValues { (_, value) ->
        when (value) {
            is JsonObject -> processJsonObject(value)
            is JsonArray -> processJsonArray(value)
            is JsonPrimitive -> processPrimitive(value)
            else -> throw IllegalArgumentException("Unsupported input value: $value")
        }
    }
}

fun processJsonObject(obj: JsonObject): Any {
    return if ("content" in obj && "isString" in obj) {
        obj["content"]?.jsonPrimitive?.content ?: ""
    } else {
        obj.mapValues { (_, v) ->
            if (v is JsonPrimitive) processPrimitive(v.jsonPrimitive)
            else throw IllegalArgumentException("Unsupported JsonObject value: $v")
        }
    }
}

fun processJsonArray(array: JsonArray): List<Any> {
    return array.map {
        when (it) {
            is JsonPrimitive -> processPrimitive(it)
            else -> throw IllegalArgumentException("Unsupported element in array: $it")
        }
    }
}

fun processPrimitive(primitive: JsonPrimitive): Any {
    return when {
        primitive.isString -> primitive.content
        primitive.booleanOrNull != null -> primitive.booleanOrNull!!
        primitive.longOrNull != null -> primitive.longOrNull!!
        primitive.doubleOrNull != null -> primitive.doubleOrNull!!
        else -> primitive.content
    }
}

fun processMeta(meta: Map<String, JsonElement>): Map<String, Any> {
    return meta.mapValues { (key, value) ->
        if (key == "title" && value is JsonObject) {
            value["content"]?.jsonPrimitive?.content ?: ""
        } else if (value is JsonPrimitive) {
            processPrimitive(value)
        } else {
            throw IllegalArgumentException("Unsupported meta field: $value")
        }
    }
}
