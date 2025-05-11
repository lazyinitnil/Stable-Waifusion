package com.example.stablewaifusionalpha.data.repository

import com.example.stablewaifusionalpha.core.common.Resource
import com.example.stablewaifusionalpha.data.mapper.toPromptBody
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.core.common.ClientIdProvider
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.repository.GenerateImageRepository
import com.example.stablewaifusionalpha.domain.usecase.GetWorkflowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerateImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val getWorkflowUseCase: GetWorkflowUseCase,
    private val clientIdProvider: ClientIdProvider
): BaseRepository(), GenerateImageRepository {

    private val clientId: String get() = clientIdProvider.clientId
    private var currentPromptId: String? = null

    override fun sendPrompt(
        style: Style,
        positiveTextPrompt: String,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat,
        imageCount: Int
    ): Flow<Resource<String>> = doRequest {
        val promptBody = style.toPromptBody(
            clientId,
            style,
            positiveTextPrompt,
            resolution,
            imageFormat,
            imageCount,
            { workflowPath -> getWorkflowUseCase.loadWorkflow(workflowPath) }
        )

        val response = apiService.sendPrompt(promptBody)
        currentPromptId = response.promptId
        response.promptId
    }

    override fun getFinalImages(
        promptId: String
    ): Flow<Resource<Map<String, List<ByteArray>>>> = doRequest {
        val historyMap = apiService.getHistory(promptId)
        val history = historyMap[promptId]

        val result = mutableMapOf<String, List<ByteArray>>()

        history?.outputs?.forEach { (nodeId, output) ->
            val images = output.images?.mapNotNull {
                val response = apiService.getImage(it.filename, it.subfolder, it.type)
                response.body()?.bytes()
            } ?: emptyList()
            result[nodeId] = images
        }
        result
    }

}