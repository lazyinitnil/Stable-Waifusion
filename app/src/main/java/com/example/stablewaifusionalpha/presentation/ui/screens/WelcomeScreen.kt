package com.example.stablewaifusionalpha.presentation.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.stablewaifusionalpha.R

@Composable
fun WelcomeScreen(onContinueClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundGrid()

        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer { alpha = 0.7f }
                .blur(radius = 16.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showTitle by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                showTitle = true
            }
            AnimatedVisibility(
                visible = showTitle,
                enter = fadeIn(tween(600)) + scaleIn(tween(600, easing = FastOutSlowInEasing), initialScale = 0.8f),
            ) {
                Text(
                    text = "Welcome to\nStable Waifusion Alpha",
                    color = Color.White,
                    style = MaterialTheme.typography.h4.copy(),
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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
    val columns = listOf(
        getImagesByPrefix("grid_ai_left") to 7f,
        getImagesByPrefix("grid_ai_center") to 7.9f,
        getImagesByPrefix("grid_ai_right") to 6.7f
    )

    Row(modifier = Modifier.fillMaxSize()) {
        columns.forEach { (images, speed ) ->
            InfiniteScrollImageColumn(
                imageRes = images,
                speed = speed,
                reverseDirection = false,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun InfiniteScrollImageColumn(
    imageRes: List<Int>,
    speed: Float,
    reverseDirection: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val adjustedSpeed = if (reverseDirection) -speed else speed

   /* val heightsDp: List<Dp> = remember(imageRes) {
        val min = 90
        val max = 180
        List(imageRes.size) {
            val a = kotlin.random.Random.nextInt(min, max + 1)
            val b = kotlin.random.Random.nextInt(min, max + 1)
            ((a + b) / 2).dp
        }
    }*/

    val imageHeights: List<Dp> = remember(imageRes) {
        val minHeight = 90.dp
        val maxHeight = 180.dp
        val baseWidth = 120.dp

        imageRes.map { resId ->
            val ratio = getImageAspectRatio(context, resId)
            (baseWidth * ratio).coerceIn(minHeight, maxHeight)
        }
    }

    LaunchedEffect(scrollState) {
        while (true) {
            delay(16)
            scrollState.scrollBy(adjustedSpeed)
            if (scrollState.firstVisibleItemIndex == imageRes.size) {
                scrollState.scrollToItem(0)
            }
        }
    }

    if (imageHeights.isNotEmpty()) {
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .graphicsLayer { alpha = 0.55f },
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val itemsCount = imageRes.size * 100

        items(itemsCount) { index ->
            val imageIndex = index % imageRes.size
            val height = imageHeights[imageIndex]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
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
        }}
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

fun getImageAspectRatio(context: Context, @DrawableRes resId: Int): Float {
    val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeResource(context.resources, resId, options)
    return if (options.outWidth > 0 && options.outHeight > 0) {
        options.outHeight.toFloat() / options.outWidth.toFloat()
    } else {
        1f
    }
}
