package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.repository.QualitiesRepository
import javax.inject.Inject

class GetQualitiesUseCase @Inject constructor(
    private val qualitiesRepository: QualitiesRepository
) {


    fun getQualities(): List<Quality> {
        return qualitiesRepository.getQualities()
    }

}