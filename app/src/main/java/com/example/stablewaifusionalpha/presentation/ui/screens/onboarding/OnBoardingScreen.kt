package com.example.stablewaifusionalpha.presentation.ui.screens.onboarding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val pages = listOf(
        OnBoardingPage(
            titleRes = R.string.onboarding_title_1,
            descRes = R.string.onboarding_desc_1,
            image = Icons.Default.AutoAwesome,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        OnBoardingPage(
            titleRes = R.string.onboarding_title_2,
            descRes = R.string.onboarding_desc_2,
            image = Icons.Default.Explore,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        OnBoardingPage(
            titleRes = R.string.onboarding_title_3,
            descRes = R.string.onboarding_desc_3,
            image = Icons.Default.People,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    )

    OnboardingPager(
        pages = pages,
        onFinish = {
            viewModel.completeOnboarding()
            onFinished()
        }
    )
}
