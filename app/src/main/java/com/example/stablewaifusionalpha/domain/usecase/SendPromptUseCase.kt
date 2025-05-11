package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.core.common.Resource
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.repository.GenerateImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendPromptUseCase @Inject constructor(
    private val generateImageRepository: GenerateImageRepository
) {

    fun sendPrompt(
        style: Style,
        positiveTextPrompt: String,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat,
        imageCount: Int
    ): Flow<Resource<String>> {
        return generateImageRepository.sendPrompt(
            style, positiveTextPrompt, resolution, imageFormat, imageCount
        )
    }



}