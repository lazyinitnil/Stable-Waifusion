package com.example.stablewaifusionalpha.data.mapper

import com.example.stablewaifusionalpha.core.ext.calculateDimensions
import com.example.stablewaifusionalpha.data.remote.dto.Text2ImageDto
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image

fun Text2Image.toText2ImageDto(
    style: Style,
    subStyle: SubStyle?,
    resolution: ResolutionLevel,
    imageFormat: ImageFormat
): Text2ImageDto {

    val loraPrompt = subStyle?.lora?.takeIf { it.isNotBlank() }?.let { ", <lora:$it:1.0>" } ?: ""
    val stylePrompt = style.stylePrompt?.takeIf { it.isNotBlank() }?.let { "$it, " } ?: ""
    val styleNegativePrompt = style.styleNegativePrompt?.takeIf { it.isNotBlank() }?.let { "$it, " } ?: ""
    val styleCfg = style.styleCfg ?: cfgScale
    val styleSteps = style.styleSteps ?: steps
    val styleSampler = style.styleSampler ?: sampler
    val styleScheduler = style.styleScheduler ?: scheduler
    val styleSDVae = style.styleSDVae
    val styleClipSkip = style.styleClipSkip
    val styleEta = style.styleEta

    val (calculatedWidth, calculatedHeight) = calculateDimensions(resolution, imageFormat)

    return Text2ImageDto(
        prompt = "$stylePrompt$prompt$loraPrompt",
        negative_prompt = "$styleNegativePrompt$negativePrompt",
        steps = styleSteps,
        cfg_scale = styleCfg,
        width = calculatedWidth,
        height = calculatedHeight,
        batch_size = batchSize,
        sampler_name = styleSampler,
        scheduler = styleScheduler,
        override_settings  = Text2ImageDto.OverrideSettings(
            sd_model_checkpoint = style.modelPath,
            CLIP_stop_at_last_layers = styleClipSkip,
            sd_vae = styleSDVae
        ),
        eta = styleEta
    )
}

