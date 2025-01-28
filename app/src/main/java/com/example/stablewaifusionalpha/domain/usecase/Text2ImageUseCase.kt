package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image
import com.example.stablewaifusionalpha.domain.repository.Text2ImageRepository
import javax.inject.Inject

class Text2ImageUseCase @Inject constructor(
    private val text2ImageRepository: Text2ImageRepository
){

    fun text2Image(
        request: Text2Image,
        style: Style,
        subStyle: SubStyle,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat
    ) =
        text2ImageRepository.text2Image(request, style, subStyle, resolution, imageFormat)

}