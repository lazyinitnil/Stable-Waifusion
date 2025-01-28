package com.example.stablewaifusionalpha.data.remote.dto

data class ImagesDto(
    val images: List<String>,
    val info: String,
    val parameters: Parameters
){
    class Parameters
}