package com.example.stablewaifusionalpha.presentation.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stablewaifusionalpha.presentation.GalleryScreen
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.ui.layout.ExpandableFab
import com.example.stablewaifusionalpha.presentation.ui.screens.SplashScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.GenerationResultScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.hub.HubScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.generation.GenerationScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.WelcomeScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.onboarding.OnBoardingScreen
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel
import com.example.stablewaifusionalpha.presentation.viewmodel.OnboardingViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    context: Context,
    generationViewModel: GenerationViewModel,
    onboardingViewModel: OnboardingViewModel,
    startDestination: String
) {
    val currentEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route

    var isFabExpanded by remember { mutableStateOf(false) }

    val window = (context as Activity).window
    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

    LaunchedEffect(currentRoute) {
        val isWelcome = currentRoute == Screen.Welcome.route
        val isSplash = currentRoute == Screen.Splash.route

        window.statusBarColor = when {
            isWelcome || isSplash -> Color.Transparent.toArgb()
            else -> Color.Black.toArgb()
        }

        WindowCompat.setDecorFitsSystemWindows(window, !(isWelcome || isSplash))
        windowInsetsController.isAppearanceLightStatusBars = !(isWelcome || isSplash)
    }

    val showBottomBar = currentRoute in listOf(Screen.Hub.route, Screen.Gallery.route)
    val showFab = showBottomBar

    Scaffold(
        floatingActionButton = {
            if (showFab) {
                ExpandableFab(
                    isExpanded = isFabExpanded,
                    onFabClick = { isFabExpanded = !isFabExpanded },
                    actions = listOf(
                        Icons.Default.TextFields to {
                            isFabExpanded = false
                            navHostController.navigate(Screen.Generation.route)
                        },
                        Icons.Default.Photo to {
                            isFabExpanded = false
                            /* TODO Image2Image */
                        },
                        Icons.Default.Videocam to {
                            isFabExpanded = false
                        /* TODO video */
                        }
                    )
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navHostController,showBottomBar)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(
                    imageRes = R.drawable.onboarding_ai_2,
                    appName = R.string.app_name,
                    onFinish = {
                        navHostController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Onboarding.route) {
                OnBoardingScreen(
                    viewModel = onboardingViewModel,
                    onFinished = {
                        onboardingViewModel.completeOnboarding()
                        navHostController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onContinueClicked = {
                        navHostController.navigate(Screen.Hub.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Hub.route) {
                HubScreen(
                    onFeatureClick = {
                        //TODO
                    },
                    onImageClick = {
                        //TODO
                    }
                )
            }

            composable(Screen.Generation.route) {
                GenerationScreen(
                    onGenerateClick = {
                        navHostController.navigate(Screen.GenerationResult.route)
                    }, viewModel = generationViewModel
                )
            }

            composable(Screen.GenerationResult.route) {
                GenerationResultScreen(
                    generationViewModel = generationViewModel
                )
            }

            composable(Screen.Gallery.route) {
                GalleryScreen(
                    context,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
