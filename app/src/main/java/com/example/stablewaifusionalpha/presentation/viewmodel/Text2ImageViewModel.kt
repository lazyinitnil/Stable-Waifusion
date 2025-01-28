package com.example.stablewaifusionalpha.presentation.viewmodel

import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Images
import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image
import com.example.stablewaifusionalpha.domain.usecase.GetPromptTemplatesUseCase
import com.example.stablewaifusionalpha.domain.usecase.GetQualitiesUseCase
import com.example.stablewaifusionalpha.domain.usecase.Text2ImageUseCase
import com.example.stablewaifusionalpha.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class Text2ImageViewModel @Inject constructor(
    private val text2ImageUseCase: Text2ImageUseCase,
    private val getQualitiesUseCase: GetQualitiesUseCase,
    private val getPromptTemplatesUseCase: GetPromptTemplatesUseCase
): BaseViewModel() {

    private val _text2ImageState = MutableStateFlow<UIState<Images>>(UIState.Empty())
    val text2ImageState = _text2ImageState

    private val _qualitiesState = MutableStateFlow<List<Quality>>(emptyList())
    val qualitiesState = _qualitiesState

    private val _promptState = MutableStateFlow("")
    val promptState = _promptState

    private val _resolutionState = MutableStateFlow(ResolutionLevel("Low",512))
    val resolutionState = _resolutionState

    private val _imageFormatState = MutableStateFlow(ImageFormat("1:1",1,1))
    val imageFormatState = _imageFormatState

    private val _batchSizeState = MutableStateFlow(1)
    val batchSizeState = _batchSizeState

    private val _qualityState = MutableStateFlow(Quality("Basic", emptyList(), emptyList(), emptyList()))
    val qualityState = _qualityState

    private val _styleState = MutableStateFlow(Style("","",R.drawable.onboarding_ai, emptyList()))
    val styleState = _styleState

    private val _subStyleState = MutableStateFlow<SubStyle?>(null)
    val subStyleState = _subStyleState

    private val _promptTemplatesState = MutableStateFlow<List<PromptTemplate>>(emptyList())
    val promptTemplatesState = _promptTemplatesState

    init {
        loadQualities()
        loadPromptTemplates()
    }

    private fun loadQualities() {
        val qualities = getQualitiesUseCase.getQualities()
        _qualitiesState.value = qualities
    }

    private fun loadPromptTemplates() {
        val templates = getPromptTemplatesUseCase.getPromptTemplatesUseCase()
        _promptTemplatesState.value = templates
    }

    fun text2Image(request: Text2Image,
                   style: Style,
                   subStyle: SubStyle,
                   resolution: ResolutionLevel,
                   imageFormat: ImageFormat
    ){
        text2ImageUseCase.text2Image(
            request,
            style,
            subStyle,
            resolution,
            imageFormat
        ).collectFlow(_text2ImageState)
    }

    fun updatePrompt(newPrompt: String) {
        _promptState.value = newPrompt
    }

    fun updateResolution(newResolution: ResolutionLevel) {
        _resolutionState.value = newResolution
    }

    fun updateImageFormat(newImageFormat: ImageFormat) {
        _imageFormatState.value = newImageFormat
    }

    fun updateBatchSize(newBatchSize: Int){
        _batchSizeState.value = newBatchSize
    }

    fun updateQuality(newQuality: Quality){
        _qualityState.value = newQuality
        updateResolution(newQuality.resolutions.first())
        updateImageFormat(newQuality.imageFormats.first())
        updateStyle(newQuality.styles.first())
    }

    fun updateStyle(newStyle: Style) {
        _styleState.value = newStyle
    }

    fun updateSubStyle(newSubStyle: SubStyle) {
        _subStyleState.value = newSubStyle
    }

}