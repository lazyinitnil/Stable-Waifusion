package com.example.stablewaifusionalpha.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.core.common.Resource
import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketEvent
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.usecase.GetPromptTemplatesUseCase
import com.example.stablewaifusionalpha.domain.usecase.GetQualitiesUseCase
import com.example.stablewaifusionalpha.domain.usecase.SendPromptUseCase
import com.example.stablewaifusionalpha.domain.usecase.GetFinalImagesUseCase
import com.example.stablewaifusionalpha.domain.usecase.ObserveWebSocketUseCase
import com.example.stablewaifusionalpha.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerationViewModel @Inject constructor(
    private val sendPromptUseCase: SendPromptUseCase,
    private val getFinalImagesUseCase: GetFinalImagesUseCase,
    private val observeWebSocketUseCase: ObserveWebSocketUseCase,
    private val getQualitiesUseCase: GetQualitiesUseCase,
    private val getPromptTemplatesUseCase: GetPromptTemplatesUseCase
): BaseViewModel() {

    private val _qualityListState = MutableStateFlow<List<Quality>>(emptyList())
    val qualityListState: StateFlow<List<Quality>> = _qualityListState

    private val _positiveTextPromptState = MutableStateFlow("")
    val positiveTextPromptState: StateFlow<String> = _positiveTextPromptState

    private val _resolutionState = MutableStateFlow(ResolutionLevel("Low",512))
    val resolutionState: StateFlow<ResolutionLevel> = _resolutionState

    private val _imageFormatState = MutableStateFlow(ImageFormat("1:1",1,1))
    val imageFormatState: StateFlow<ImageFormat> = _imageFormatState

    private val _imageCountState = MutableStateFlow(1)
    val imageCountState: StateFlow<Int> = _imageCountState

    private val _qualityState = MutableStateFlow(Quality("Basic", emptyList(), emptyList(), emptyList()))
    val qualityState: StateFlow<Quality> = _qualityState

    private val _styleState = MutableStateFlow(Style("",R.drawable.onboarding_ai, "" ))
    val styleState: StateFlow<Style> = _styleState

    private val _promptTemplateListState = MutableStateFlow<List<PromptTemplate>>(emptyList())
    val promptTemplateListState: StateFlow<List<PromptTemplate>> = _promptTemplateListState

    init {
        loadQualities()
        loadPromptTemplates()
    }

    private fun loadQualities() {
        val qualities = getQualitiesUseCase.getQualities()
        _qualityListState.value = qualities
    }

    private fun loadPromptTemplates() {
        val templates = getPromptTemplatesUseCase.getPromptTemplatesUseCase()
        _promptTemplateListState.value = templates
    }

    private val _promptIdState = MutableStateFlow<UIState<String>>(UIState.Empty())
    val promptIdState: StateFlow<UIState<String>> = _promptIdState

    private val _generationProgressState = MutableStateFlow(0)
    val generationProgressState: StateFlow<Int> = _generationProgressState

    private val _livePreviewImages = MutableStateFlow<List<Bitmap>>(emptyList())
    val livePreviewImages: StateFlow<List<Bitmap>> = _livePreviewImages

    private val _finalImagesState = MutableStateFlow<UIState<List<Bitmap>>>(UIState.Empty())
    val finalImagesState: StateFlow<UIState<List<Bitmap>>> = _finalImagesState

    private var currentPromptId: String? = null

    fun sendPrompt(
        style: Style,
        positiveTextPrompt: String,
        resolution: ResolutionLevel,
        imageFormat: ImageFormat,
        imageCount: Int
    ) {
      /*  _livePreviewImages.value = List(imageCount) { mutableListOf() }*/
        _livePreviewImages.value = emptyList()
//        _finalImages.value = emptyList()
        _finalImagesState.value = UIState.Loading()
        _promptIdState.value = UIState.Loading()

        sendPromptUseCase.sendPrompt(
            style, positiveTextPrompt, resolution, imageFormat, imageCount
        ).onEach { resource ->
            if (resource is Resource.Success) {
                val promptId = resource.data ?: return@onEach
                currentPromptId = promptId
                _promptIdState.value = UIState.Success(promptId)
                observeGenerationEvents(promptId)
            }
        }.collectFlow(_promptIdState)
    }

    private fun observeGenerationEvents(promptId: String) {
        viewModelScope.launch {
            observeWebSocketUseCase.observeGenerationEvents(promptId).collect { event ->
                when (event) {
                    is WebSocketEvent.Progress -> _generationProgressState.value = event.percent
                    is WebSocketEvent.LivePreview -> updateLivePreviewImages(event.imageData)
                    is WebSocketEvent.Done -> getFinalImages(promptId)
                    is WebSocketEvent.Error -> {
                        Log.e("WebSocket", "Error: ${event.message}")
                    }
                }
            }
        }
    }

    private fun getFinalImages(promptId: String) {
        getFinalImagesUseCase.getFinalImages(promptId)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val bitmaps = resource.data
                            ?.values
                            ?.flatten()
                            ?.mapNotNull { bytes ->
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            }
                            .orEmpty()
                        Resource.Success(bitmaps)
                    }
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
                }
            }
            .collectFlow(_finalImagesState)

    }

    private fun updateLivePreviewImages(imageData: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        if (bitmap != null) {
            _livePreviewImages.update { it + bitmap }/*{ list ->
                if (index in list.indices) {
                    list.toMutableList().also { it[index].add(bitmap) }
                } else list*/

        }
    }

    fun updatePositiveTextPrompt(newPositiveTextPrompt: String) {
        _positiveTextPromptState.value = newPositiveTextPrompt
    }

    fun updateResolution(newResolution: ResolutionLevel) {
        _resolutionState.value = newResolution
    }

    fun updateImageFormat(newImageFormat: ImageFormat) {
        _imageFormatState.value = newImageFormat
    }

    fun updateImageCount(newImageCount: Int){
        _imageCountState.value = newImageCount
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

}
