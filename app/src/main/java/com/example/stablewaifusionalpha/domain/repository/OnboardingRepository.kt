package com.example.stablewaifusionalpha.domain.repository

interface OnboardingRepository {
    suspend fun getOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(isCompleted: Boolean)
}