package com.example.stablewaifusionalpha.data.remote.dto

data class Text2ImageDto(
    val prompt: String,
    val negative_prompt: String,
    val steps: Int,
    val cfg_scale: Float,
    val width: Int,
    val height: Int,
    val batch_size: Int,
    val sampler_name: String,
    val scheduler: String,
    val override_settings: OverrideSettings,
    val eta: Int? = null,
    val save_images: Boolean = true
) {
    data class OverrideSettings(
        val sd_model_checkpoint: String,
        val sd_vae: String? = null,
        val CLIP_stop_at_last_layers: Int? = null,
        val return_grid: Boolean = false
    )
}
