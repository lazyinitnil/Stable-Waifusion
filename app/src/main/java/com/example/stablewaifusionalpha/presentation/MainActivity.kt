package com.example.stablewaifusionalpha.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.navigation.NavGraph
import com.example.stablewaifusionalpha.presentation.navigation.Screen
import com.example.stablewaifusionalpha.presentation.ui.screens.SplashScreen
import com.example.stablewaifusionalpha.presentation.ui.theme.StableWaifusionAlphaTheme
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel
import com.example.stablewaifusionalpha.presentation.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StableWaifusionAlphaTheme {
                MainApp(context = this)
            }
        }
    }
}

@Composable
fun MainApp(context: Context) {
    val navController = rememberNavController()

    val generationViewModel: GenerationViewModel = hiltViewModel()
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val onboardingCompleted by onboardingViewModel.onboardingCompleted.collectAsState(initial = false)
    val isLoading by onboardingViewModel.loading.collectAsState(initial = true)

    when {
        isLoading -> {
            SplashScreen(
                imageRes = R.drawable.onboarding_ai_2,
                appName = R.string.app_name,
                onFinish = {}
            )
        }
        else -> {
            val startDestination = if (onboardingCompleted) Screen.Splash.route else Screen.Onboarding.route
            NavGraph(
                navHostController = navController,
                context = context,
                generationViewModel = generationViewModel,
                onboardingViewModel = onboardingViewModel,
                startDestination = startDestination
            )
        }
    }
}


@Composable
fun GalleryScreen(context: Context, modifier: Modifier) {
    Surface(
        Modifier.fillMaxSize(),
        contentColor = Color.LightGray,
        color = Color.DarkGray
    ){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Gallery Screen", style = MaterialTheme.typography.h4)
    }}
}

/*fun enableEdgeToEdge(context: Context, isEnable: Boolean) {
    val activity = context as? ComponentActivity
    activity?.window?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isEnable){
                it.setDecorFitsSystemWindows(false)
            } else {
                @Suppress("DEPRECATION")
                it.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        )
            }
        }
    }
}*/
