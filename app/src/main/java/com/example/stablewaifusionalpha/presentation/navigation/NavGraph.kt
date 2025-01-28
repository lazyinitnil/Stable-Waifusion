package com.example.stablewaifusionalpha.presentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stablewaifusionalpha.presentation.GalleryScreen
import com.example.stablewaifusionalpha.presentation.viewmodel.ImageGenViewModel
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.ui.screens.AnimatedImageWithBlur
import com.example.stablewaifusionalpha.presentation.ui.screens.EditPromptScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.GeneratedImagesScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.HomeScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.ImageToImageScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.TextToImageScreen
import com.example.stablewaifusionalpha.presentation.ui.screens.WelcomeScreen
import com.example.stablewaifusionalpha.presentation.viewmodel.Text2ImageViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    context: Context,
    viewModel: ImageGenViewModel,
    text2ImageViewModel: Text2ImageViewModel
) {
    NavHost(navController = navHostController, startDestination = ROUTES.SPLASH_ROUTE) {

        composable(ROUTES.SPLASH_ROUTE) {
            AnimatedImageWithBlur(
                imageRes = R.drawable.onboarding_ai_2,
                appName = R.string.app_name,
                onNavigateToMain = {
                    navHostController.navigate(ROUTES.WELCOME_ROUTE) {
                        popUpTo(ROUTES.SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTES.WELCOME_ROUTE) {
            WelcomeScreen(
                onContinueClicked = {
                    navHostController.navigate(ROUTES.HOME_ROUTE) {
                        popUpTo(ROUTES.WELCOME_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTES.HOME_ROUTE) {
            HomeScreen(
                context,
                modifier = Modifier.fillMaxSize() ,
                onText2ImageCardClick = {
                    navHostController.navigate(ROUTES.TEXT_TO_IMAGE_ROUTE)
                },
                onImage2ImageCardClick = {
                    navHostController.navigate(ROUTES.IMAGE_TO_IMAGE_ROUTE)
                }
            )
        }

        composable(ROUTES.TEXT_TO_IMAGE_ROUTE){
            TextToImageScreen(
                modifier = Modifier.fillMaxSize(),
                onGenerateClick = {
                    navHostController.navigate(ROUTES.GENERATED_IMAGES_ROUTE)
                }, viewModel = text2ImageViewModel
            )
        }

        composable(ROUTES.IMAGE_TO_IMAGE_ROUTE){
            ImageToImageScreen(
                context,
                modifier = Modifier.fillMaxSize()
            )
        }


        composable(ROUTES.GENERATED_IMAGES_ROUTE){
            GeneratedImagesScreen(
                modifier = Modifier.fillMaxSize(), viewModel = text2ImageViewModel
            )
        }

        /*composable(ROUTES.ADVANCED_GEN_ROUTE) {
            AdvancedGenerationScreen(
                context,
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onEditPrompt = { isNegativePrompt ->
                    navHostController.navigate(
                        "${ROUTES.EDIT_PROMPT_ROUTE}/$isNegativePrompt"
                    )
                }
            )
        }*/

        composable(ROUTES.GALLERY_ROUTE){
            GalleryScreen(
                context,
                modifier = Modifier.fillMaxSize()
            )
        }

       /* composable("${ROUTES.EDIT_PROMPT_ROUTE}/{isNegativePrompt}") { backStackEntry ->
            val isNegativePrompt = backStackEntry.arguments?.getString("isNegativePrompt").toBoolean()
            EditPromptScreen(
                viewModel = viewModel,
                onSave = {
                    navHostController.popBackStack()
                },
                isNegativePrompt = isNegativePrompt
            )
        }*/

    }
}
