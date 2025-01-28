package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate

interface PromptTemplatesRepository {

    fun getPromptTemplates(): List<PromptTemplate>

}