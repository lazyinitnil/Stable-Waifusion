package com.example.stablewaifusionalpha.presentation.ui.screens

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.stablewaifusionalpha.R

@Composable
fun WelcomeScreen(onContinueClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundGrid()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Stable Waifusion Alpha",
                color = Color.White,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Explore the powerful AI art generator",
                color = Color.Gray,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF6A00F4), Color(0xFFBC00E0))
                        )
                    )
                    .clickable(onClick = onContinueClicked),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Continue", color = Color.White, style = MaterialTheme.typography.button)
            }
        }
    }
}




@Composable
fun BackgroundGrid() {
    val leftColumnImages = getImagesByPrefix("grid_ai_left")
    val centerColumnImages = getImagesByPrefix("grid_ai_center")
    val rightColumnImages = getImagesByPrefix("grid_ai_right")

    Row(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        Column(modifier = Modifier.weight(1f)) {
            InfiniteScrollImageColumn(
                imageRes = leftColumnImages,
                speed = 5.7f,
                reverseDirection = false
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            InfiniteScrollImageColumn(
                imageRes = centerColumnImages,
                speed = 6.7f,
                reverseDirection = false
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            InfiniteScrollImageColumn(
                imageRes = rightColumnImages,
                speed = 5.3f,
                reverseDirection = false
            )
        }
    }
}

@Composable
fun InfiniteScrollImageColumn(
    imageRes: List<Int>,
    speed: Float,
    reverseDirection: Boolean = false
) {
    val scrollState = rememberLazyListState()
    val adjustedSpeed = if (reverseDirection) -speed else speed

    LaunchedEffect(scrollState) {
        while (true) {
            delay(16)
            scrollState.scrollBy(adjustedSpeed)

            if (scrollState.firstVisibleItemIndex == imageRes.size) {
                scrollState.scrollToItem(0)
            }
        }
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val itemsCount = imageRes.size * 100

        items(itemsCount) { index ->
            val imageIndex = index % imageRes.size
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes[imageIndex]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}



fun getImagesByPrefix( prefix: String): List<Int> {
    val drawables = mutableListOf<Int>()
    val fields = R.drawable::class.java.fields

    for (field in fields) {
        if (field.name.startsWith(prefix)) {
            try {
                val resourceId = field.getInt(null)
                drawables.add(resourceId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return drawables
}