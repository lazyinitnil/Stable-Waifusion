package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.core.Resource
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Images
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image
import kotlinx.coroutines.flow.Flow

interface Text2ImageRepository {

    fun text2Image(
        request: Text2Image,
        style: Style,
        subStyle: SubStyle,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat
    ): Flow<Resource<Images>>

}