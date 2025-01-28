package com.example.stablewaifusionalpha.data.mapper

import com.example.stablewaifusionalpha.data.remote.dto.UpscalerDto
import com.example.stablewaifusionalpha.domain.model.remote.Upscaler

fun UpscalerDto.toUpscaler() = Upscaler(
    name = name
)