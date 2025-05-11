package com.example.stablewaifusionalpha.presentation.ui.screens.hub

import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.ui.theme.Shapes
import com.example.stablewaifusionalpha.presentation.ui.theme.backgroundVariant
import kotlinx.coroutines.delay

@Composable
fun HubScreen(
    onFeatureClick: (String) -> Unit,
    onImageClick: (String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.backgroundVariant)
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                //.padding(bottom = 72.dp)
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    AnimatedVisibility(
                        visible,
                        enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        AppFeaturesShowcaseBanner()
                    }
                }
                item {
                    AnimatedVisibility(
                        visible,
                        enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        FeatureGrid(
                            onFeatureClick
                        )
                    }
                }
                item {
                    AnimatedVisibility(
                        visible,
                        enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        TopGenerationsCarousel(
                            onImageClick
                        )
                    }
                }
                item {
                    AnimatedVisibility(
                        visible,
                        enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        ChallengeOfTheWeek()
                    }
                }
                item {
                    AnimatedVisibility(
                        visible,
                        enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        RecommendedPresetsSection()
                    }
                }
            }
        }
    }
}

@Composable
fun AppFeaturesShowcaseBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                Brush.linearGradient(
                    listOf(Color.Magenta, Color.Cyan)
                ),
               // shape = Shapes.medium
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "✨ Explore the power of " + stringResource(R.string.app_name),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FeatureGrid(
    onFeatureClick: (String) -> Unit
) {
    val features = listOf(
        "Text2Image" to Icons.Default.TextFields,
        "Image2Image" to Icons.Default.Photo,
        "Video" to Icons.Default.Videocam,
        "Audio" to Icons.Default.Mic,
        "Avatar" to Icons.Default.Face,
        "AR" to Icons.Default.PhoneIphone
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(features) { (label, icon) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp)
                    .clickable { onFeatureClick(label) }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }
        }
    }
}

@Composable
fun TopGenerationsCarousel(
    onImageClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🏆" + stringResource(R.string.best_generations), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items((1..5).toList()) { index ->
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(Shapes.medium)
                        .background(Color.DarkGray)
                        .clickable { onImageClick("generation_$index") }
                )
            }
        }
    }
}

@Composable
fun ChallengeOfTheWeek() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🔥" + stringResource(R.string.challenge_of_the_week), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF3C1F4E)),
            contentAlignment = Alignment.Center
        ) {
            Text("Theme: Cyberpunk World", color = Color.White)
        }
    }
}

@Composable
fun RecommendedPresetsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("🎁" + stringResource(R.string.recommended_styles), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items((1..3).toList()) { index ->
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(140.dp, 80.dp)
                        .background(Color(0xFF2A2A3A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${stringResource(R.string.style)} $index", color = Color.White)
                }
            }
        }
    }
}


/*{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Row(Modifier.fillMaxWidth(), Arrangement.Center)
                { Text(
                    stringResource(id = R.string.generation_modes),
                    color = Purple40,
                    fontSize = 24.sp
                ) } },
                backgroundColor = Color.Black,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    GenerationCard(
                        stringResource(id = R.string.text_to_image),
                        ImageBitmap.imageResource(R.drawable.grid_ai_left_3)
                    ) { onText2ImageCardClick() }

                    Spacer(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp))

                    GenerationCard(
                        stringResource(id = R.string.image_to_image),
                        ImageBitmap.imageResource(R.drawable.grid_ai_left_11)
                    ) { onImage2ImageCardClick() }

                }
            }

        }
    )

}*/

/*
@Composable
fun GenerationCard(
    generationType: String,
    generationPreview: ImageBitmap,
    onGenerationClick: () -> Unit
){
    CardWithAnimatedBorder(
        modifier = Modifier.fillMaxWidth(),
        onCardClick = {onGenerationClick()},
        borderColors = listOf(Color.Cyan, Purple40, Color.Blue, Color.Magenta, Color.Yellow),
        externalShape = RoundedCornerShape(4.dp),
        innerShape = RoundedCornerShape(4.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        ) {

            Image(generationPreview, contentDescription = null, modifier = Modifier
                .fillMaxSize(),
                contentScale = ContentScale.FillWidth)

            Text(
                generationType, modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(8.dp),
                fontSize = 16.sp,
                minLines = 2,
                color = Color.White
            )
        }
    }
}*/
