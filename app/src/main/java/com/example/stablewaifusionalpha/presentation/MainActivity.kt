package com.example.stablewaifusionalpha.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stablewaifusionalpha.presentation.navigation.BottomNavBar
import com.example.stablewaifusionalpha.presentation.navigation.NavGraph
import com.example.stablewaifusionalpha.presentation.navigation.ROUTES
import com.example.stablewaifusionalpha.presentation.viewmodel.ImageGenViewModel
import com.example.stablewaifusionalpha.presentation.viewmodel.Text2ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp(context = this)
        }
    }
}

@Composable
fun MainApp(context: Context) {
    val navController = rememberNavController()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val viewModel: ImageGenViewModel = viewModel()
    val text2ImageViewModel: Text2ImageViewModel = hiltViewModel()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    /*when(currentRoute){
        ROUTES.SPLASH_ROUTE -> enableEdgeToEdge(context,isEnable = true)
        else -> enableEdgeToEdge(context,isEnable = false)
    }*/

   Scaffold(
       bottomBar = {
           when(currentRoute){
               ROUTES.HOME_ROUTE, ROUTES.GALLERY_ROUTE -> {
                   BottomNavBar(navController = navController)
               }
           }
       }
   ) { innerPadding ->
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding)
               .background(Color.Black)
       )
       NavGraph(
           navHostController = navController,
           context = context,
           viewModel = viewModel,
           text2ImageViewModel = text2ImageViewModel
       )
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

@Composable
fun ColoredText(text: String, color: Color = Color.White, textAlign: TextAlign? = null, modifier: Modifier = Modifier, fontSize: TextUnit = TextUnit.Unspecified, ) {
    ProvideTextStyle(TextStyle(color = color)) {
        Text(text = text, modifier = modifier, fontSize = fontSize, textAlign = textAlign)
    }
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
