package com.example.stablewaifusionalpha.presentation.viewmodel

import com.example.stablewaifusionalpha.domain.model.remote.Upscaler
import com.example.stablewaifusionalpha.domain.usecase.GetUpscalersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.stablewaifusionalpha.presentation.UIState
import kotlinx.coroutines.flow.MutableStateFlow


@HiltViewModel
class UpscalerViewModel @Inject constructor(
    private val getUpscalersUseCase: GetUpscalersUseCase
): BaseViewModel() {

    private val _getUpscalersState = MutableStateFlow<UIState<List<Upscaler>>>(UIState.Empty())
    val getUpscalersState = _getUpscalersState

    fun getUpscalers(){
        getUpscalersUseCase.getUpscalers().collectFlow(_getUpscalersState)
    }

}