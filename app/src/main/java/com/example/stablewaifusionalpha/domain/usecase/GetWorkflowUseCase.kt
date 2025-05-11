package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.data.remote.dto.Node
import com.example.stablewaifusionalpha.domain.repository.WorkflowRepository
import javax.inject.Inject

class GetWorkflowUseCase @Inject constructor(
    private val workflowRepository: WorkflowRepository
) {

    suspend fun loadWorkflow(workflowPath: String): Map<String, Node> {
        return workflowRepository.loadWorkflow(workflowPath)
    }

}