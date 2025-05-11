package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.core.common.Resource
import com.example.stablewaifusionalpha.domain.repository.GenerateImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFinalImagesUseCase @Inject constructor(
    private val generateImageRepository: GenerateImageRepository
) {

   fun getFinalImages(promptId: String):  Flow<Resource<Map<String, List<ByteArray>>>> {
        return generateImageRepository.getFinalImages(promptId)
    }

}