package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate
import com.example.stablewaifusionalpha.domain.repository.PromptTemplatesRepository
import javax.inject.Inject

class GetPromptTemplatesUseCase @Inject constructor(
    private val promptTemplatesRepository: PromptTemplatesRepository
) {

    fun getPromptTemplatesUseCase(): List<PromptTemplate>{
        return promptTemplatesRepository.getPromptTemplates()
    }

}