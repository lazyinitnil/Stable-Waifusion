package com.example.stablewaifusionalpha.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.stablewaifusionalpha.domain.usecase.OnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase
) : BaseViewModel() {

    private val _onboardingCompleted = MutableStateFlow(false)
    val onboardingCompleted: StateFlow<Boolean> = _onboardingCompleted

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            _onboardingCompleted.value = onboardingUseCase.isOnboardingCompleted()
            _loading.value = false
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingUseCase.completeOnboarding()
            _onboardingCompleted.value = true
        }
    }

}

