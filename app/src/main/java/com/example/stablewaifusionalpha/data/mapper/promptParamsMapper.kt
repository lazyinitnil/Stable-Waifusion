package com.example.stablewaifusionalpha.data.mapper

import com.example.stablewaifusionalpha.core.utils.calculateDimensions
import com.example.stablewaifusionalpha.data.remote.dto.Node
import com.example.stablewaifusionalpha.data.remote.dto.PromptBody
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import kotlinx.coroutines.runBlocking

fun Style.toPromptBody(
    clientId: String,
    style: Style,
    positiveTextPrompt: String,
    resolution: ResolutionLevel,
    imageFormat: ImageFormat,
    imageCount: Int,
    loadWorkflow: suspend (String) -> Map<String, Node>
): PromptBody{
    val workflowJson = runBlocking { loadWorkflow(style.workflowPath) }.toMutableMap()

    val promptParams: MutableMap<String, Any> = workflowJson.mapValues { it.value.toMap() }.toMutableMap()

    val (calculatedWidth, calculatedHeight) = calculateDimensions(resolution, imageFormat)

    val stylePrompt = style.stylePrompt?.takeIf { it.isNotBlank() }?.let { "$it, " } ?: ""

    updateNodeInputByTitle(promptParams, titleContains = "Positive Prompt") { inputs ->
        inputs["text"] = "$stylePrompt$positiveTextPrompt"
    }
    updateNodeInputByTitle(promptParams, titleContains = "Пустое латентное изображение") { inputs ->
        inputs["batch_size"] = imageCount
    }

    /*updateNodeInputByTitle(promptParams, titleContains = "Model Loader") { inputs ->
        val modelName = style.findParamByTitleAndName(
            "Model Loader", "ckpt_name"
        ) ?: BASIC_REALISM_MODEL_PATH
        inputs["ckpt_name"] = modelName
    }*/

    updateNodeInputByTitle(promptParams, titleContains = "Пустое латентное изображение") { inputs ->
        inputs["width"] = calculatedWidth
        inputs["height"] = calculatedHeight
    }

    style.applyStyleParams(promptParams)

    return PromptBody(
        prompt = promptParams,
        clientId = clientId
    )
}

private fun updateNodeInputByTitle(
    promptParams: MutableMap<String, Any>,
    titleContains: String,
    updateAction: (MutableMap<String, Any>) -> Unit
) {
    for ((_, nodeData) in promptParams) {
        val node = nodeData as? MutableMap<String, Any> ?: continue
        val meta = node["_meta"] as? MutableMap<String, Any> ?: continue
        val title = meta["title"] as? String ?: continue

        if (title.contains(titleContains, ignoreCase = true)) {
            val inputs = node["inputs"] as? MutableMap<String, Any> ?: continue
            updateAction(inputs)
        }
    }
}

private fun Style.applyStyleParams(promptParams: MutableMap<String, Any>) {
    for (param in styleParams) {
        updateNodeInputByTitle(promptParams, param.title) { inputs ->
            inputs[param.paramName] = param.paramValue
        }
    }
}