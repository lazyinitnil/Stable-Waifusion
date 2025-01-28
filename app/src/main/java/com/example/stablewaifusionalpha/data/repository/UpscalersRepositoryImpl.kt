package com.example.stablewaifusionalpha.data.repository

import com.example.stablewaifusionalpha.core.Resource
import com.example.stablewaifusionalpha.data.mapper.toUpscaler
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.domain.model.remote.Upscaler
import com.example.stablewaifusionalpha.domain.repository.UpscalersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpscalersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BaseRepository(), UpscalersRepository {

    override fun getUpscalers(): Flow<Resource<List<Upscaler>>> = doRequest {
        apiService.getUpscalers().map { it.toUpscaler() }
    }

}