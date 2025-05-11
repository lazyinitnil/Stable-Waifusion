package com.example.stablewaifusionalpha.data.remote

import com.example.stablewaifusionalpha.core.constants.PROMPT
import com.example.stablewaifusionalpha.data.remote.dto.HistoryResponseDto
import com.example.stablewaifusionalpha.data.remote.dto.PromptBody
import com.example.stablewaifusionalpha.data.remote.dto.PromptResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST(PROMPT)
    suspend fun sendPrompt(
        @Body body: PromptBody
    ): PromptResponseDto

    @GET("history/{promptId}")
    suspend fun getHistory(
        @Path("promptId") promptId: String
    ): Map<String, HistoryResponseDto>

    @GET("view")
    suspend fun getImage(
        @Query("filename") filename: String,
        @Query("subfolder") subfolder: String,
        @Query("type") type: String = "output"
    ): Response<ResponseBody>

}