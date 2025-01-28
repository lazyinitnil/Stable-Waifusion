package com.example.stablewaifusionalpha.data.mapper

import com.example.stablewaifusionalpha.data.remote.dto.ImagesDto
import com.example.stablewaifusionalpha.domain.model.remote.Images

fun ImagesDto.toImages() = Images(
    images = images
)
