package com.example.stablewaifusionalpha.data.repository

import com.example.stablewaifusionalpha.core.Resource
import com.example.stablewaifusionalpha.data.mapper.toImages
import com.example.stablewaifusionalpha.data.mapper.toText2ImageDto
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Images
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image
import com.example.stablewaifusionalpha.domain.repository.Text2ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Text2ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BaseRepository(), Text2ImageRepository {

    override fun text2Image(
        request: Text2Image,
        style: Style,
        subStyle: SubStyle,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat
    ): Flow<Resource<Images>> = doRequest {
        val updatedRequest = request.toText2ImageDto(style,subStyle,resolution,imageFormat)
        apiService.text2Image(updatedRequest).toImages()
    }

}