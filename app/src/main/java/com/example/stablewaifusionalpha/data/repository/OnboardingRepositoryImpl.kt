package com.example.stablewaifusionalpha.data.repository

import com.example.stablewaifusionalpha.data.local.preference.AppPreferences
import com.example.stablewaifusionalpha.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : OnboardingRepository {

    override suspend fun getOnboardingCompleted(): Boolean {
        return appPreferences.onboardingCompleted.first()
    }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        appPreferences.setOnboardingCompleted(isCompleted)
    }

}