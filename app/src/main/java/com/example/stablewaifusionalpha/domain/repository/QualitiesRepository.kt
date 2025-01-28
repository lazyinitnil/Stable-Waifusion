package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.domain.model.remote.Quality

interface QualitiesRepository {

    fun getQualities(): List<Quality>

}