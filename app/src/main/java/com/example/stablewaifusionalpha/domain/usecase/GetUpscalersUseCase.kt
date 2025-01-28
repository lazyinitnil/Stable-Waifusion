package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.domain.repository.UpscalersRepository
import javax.inject.Inject

class GetUpscalersUseCase @Inject constructor(
    private val upscalersRepository: UpscalersRepository
) {

    fun getUpscalers() = upscalersRepository.getUpscalers()

}