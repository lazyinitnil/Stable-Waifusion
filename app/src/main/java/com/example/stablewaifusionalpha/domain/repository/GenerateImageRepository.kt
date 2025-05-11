package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.core.common.Resource
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import kotlinx.coroutines.flow.Flow

interface GenerateImageRepository {

    fun sendPrompt(
        style: Style,
        positiveTextPrompt: String,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat,
        imageCount: Int
    ): Flow<Resource<String>>

    fun getFinalImages(
        promptId: String
    ):  Flow<Resource<Map<String, List<ByteArray>>>>

}