package com.example.stablewaifusionalpha.data.remote

import com.example.stablewaifusionalpha.core.SAMPLERS
import com.example.stablewaifusionalpha.core.TXT2IMG
import com.example.stablewaifusionalpha.data.remote.dto.ImagesDto
import com.example.stablewaifusionalpha.data.remote.dto.Text2ImageDto
import com.example.stablewaifusionalpha.data.remote.dto.UpscalerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST(TXT2IMG)
    suspend fun text2Image(@Body request: Text2ImageDto): ImagesDto

    @GET(SAMPLERS)
    suspend fun getUpscalers(): List<UpscalerDto>

}