package com.example.stablewaifusionalpha.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageGenViewModel : ViewModel() {

    private val _prompt = MutableLiveData("")
    val prompt: LiveData<String> get() = _prompt

    private val _negativePrompt = MutableLiveData("")
    val negativePrompt: LiveData<String> get() = _negativePrompt

    val steps = MutableLiveData<Int>()
    val cfgScale = MutableLiveData<Float>()
    val width = MutableLiveData<Int>()
    val height = MutableLiveData<Int>()
    val batchSize = MutableLiveData<Int>()
    val seed = MutableLiveData<Int>()

    fun updatePrompt(newPrompt: String) {
        _prompt.value = newPrompt
    }

    fun updateNegativePrompt(newNegativePrompt: String){
        _negativePrompt.value = newNegativePrompt
    }
}