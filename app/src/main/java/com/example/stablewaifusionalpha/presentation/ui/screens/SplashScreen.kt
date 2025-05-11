package com.example.stablewaifusionalpha.presentation.ui.screens

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(
    imageRes: Int,
    @StringRes appName: Int,
    onFinish: () -> Unit
) {
    val blurRadius = remember { Animatable(20f) }
    val textAlpha = remember { Animatable(0f) }

    val textLines = appName.toString().split(" ")

    LaunchedEffect(Unit) {
        blurRadius.animateTo(0f, animationSpec = tween(1400))
        textAlpha.animateTo(1f, animationSpec = tween(800))
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
                )
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "img",
            modifier = Modifier
                .fillMaxSize() //
                .graphicsLayer { alpha = 1f }
                .blur(blurRadius.value.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            textLines.forEach { line ->
                Text(
                    text = stringResource(id = appName),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        letterSpacing = 1.2.sp
                    ),
                    color = Color(0xFF6200EE),
                    modifier = Modifier
                        .alpha(textAlpha.value)
                        .padding(bottom = 32.dp)
                )
            }
        }
    }
}
