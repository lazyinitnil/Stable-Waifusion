package com.example.stablewaifusionalpha.domain.repository

import com.example.stablewaifusionalpha.data.remote.dto.Node

interface WorkflowRepository {
    suspend fun loadWorkflow(workflowPath: String): Map<String, Node>
}
