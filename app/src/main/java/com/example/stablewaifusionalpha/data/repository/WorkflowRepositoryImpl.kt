package com.example.stablewaifusionalpha.data.repository

import android.content.Context
import com.example.stablewaifusionalpha.data.remote.dto.Node
import com.example.stablewaifusionalpha.domain.repository.WorkflowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WorkflowRepositoryImpl @Inject constructor(
    private val context: Context
) : WorkflowRepository {

    override suspend fun loadWorkflow(workflowPath: String): Map<String, Node> {
        return withContext(Dispatchers.IO) {
            val json = context.assets.open(workflowPath).bufferedReader().use { it.readText() }
            Json.decodeFromString<Map<String, Node>>(json)
        }
    }
}
