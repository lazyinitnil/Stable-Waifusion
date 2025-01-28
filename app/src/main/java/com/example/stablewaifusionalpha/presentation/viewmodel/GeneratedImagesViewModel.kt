package com.example.stablewaifusionalpha.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

class GeneratedImagesViewModel(): BaseViewModel(){

    private val _isGenerationLoadingState = MutableStateFlow(true)
    val isGenerationLoadingState = _isGenerationLoadingState

    private val _isGenerationCompleteState = MutableStateFlow(false)
    val isGenerationCompleteState = _isGenerationCompleteState

    fun updateIsGenerationLoading(isLoading: Boolean){
        _isGenerationLoadingState.value = isLoading
    }

    fun updateIsGenerationComplete(isComplete: Boolean){
        _isGenerationCompleteState.value = isComplete
    }

}