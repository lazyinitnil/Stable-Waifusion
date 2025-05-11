package com.example.stablewaifusionalpha.presentation.ui.screens.onboarding

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class OnBoardingPage(
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int,
    val image: ImageVector,
    val backgroundColor: Color
)
