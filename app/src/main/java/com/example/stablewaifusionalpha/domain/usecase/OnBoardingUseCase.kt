package com.example.stablewaifusionalpha.domain.usecase

import com.example.stablewaifusionalpha.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend fun isOnboardingCompleted(): Boolean {
        return onboardingRepository.getOnboardingCompleted()
    }

    suspend fun completeOnboarding() {
        onboardingRepository.setOnboardingCompleted(true)
    }
}

