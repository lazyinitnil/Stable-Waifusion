package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.core.Resource
import com.example.stablewaifusionalpha.domain.model.remote.Upscaler
import kotlinx.coroutines.flow.Flow

interface UpscalersRepository {

    fun getUpscalers(): Flow<Resource<List<Upscaler>>>

}