package com.example.stablewaifusionalpha.presentation.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.presentation.UIState
import com.example.stablewaifusionalpha.presentation.ui.common.CardWithAnimatedBorder
import com.example.stablewaifusionalpha.presentation.ui.common.StateHandler
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple40
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple80
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel


@Composable
fun GenerationResultScreen(
    generationViewModel: GenerationViewModel
){
    val imageFormat by generationViewModel.imageFormatState.collectAsState()
    val imagesCount by generationViewModel.imageCountState.collectAsState()
    val generationProgress by generationViewModel.generationProgressState.collectAsState()
    val previewImages by generationViewModel.livePreviewImages.collectAsState()
    val finalImagesState by generationViewModel.finalImagesState.collectAsState()
    val promptIdState by generationViewModel.promptIdState.collectAsState()

    val finalImages: List<Bitmap> = when (finalImagesState) {
        is UIState.Success -> (finalImagesState as UIState.Success<List<Bitmap>>).data
        else -> emptyList()
    }

    val hasFinalImages = finalImages.isNotEmpty()

    /*val screenHeight = LocalConfiguration.current.screenHeightDp.dp*/
    var selectedImageIndex by remember { mutableStateOf(0) }


    Scaffold(
        bottomBar = {
            if (hasFinalImages) {
                BottomAppBar(
                    modifier = Modifier.height(80.dp),
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    brush = Brush.linearGradient(listOf(Color.Cyan, Purple40))
                                ),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ImproveImageButton()
                        }
                    },
                    backgroundColor = Color.Black
                )
            }
        },
        content = { innerPadding ->
            StateHandler(
                state = promptIdState,
                onSuccess = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.Black)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            when {
                                hasFinalImages -> {
                                    finalImages.getOrNull(selectedImageIndex)?.let {
                                        ShowImages(
                                            image = it,
                                            imageFormat = imageFormat,
                                            imagesCount = imagesCount
                                        )
                                    }

                                    if (finalImages.size > 1) {
                                        ThumbnailsOfImagesRow(
                                            images = finalImages,
                                            selectedImageIndex = selectedImageIndex,
                                            onThumbnailClick = { selectedImageIndex = it }
                                        )
                                    }
                                }

                                previewImages.isNotEmpty() -> {
                                    ShowLivePreviewImages(
                                        images = previewImages,
                                        imageFormat = imageFormat,
                                        generationProgress = generationProgress
                                    )

                            }  else -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    GenerationLoading()

                                }
                            }
                            }
                        }
                    }
                            },
                onLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                       /* GenerationLoading()*/
                    }
                },
                onError = { error ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: $error", color = Color.Red)
                    }
                }
            )
        }
    )
}

@Composable
fun ShowImages(
    image: Bitmap,
    imageFormat: ImageFormat,
    imagesCount: Int
) {

    val aspectRatio = imageFormat.widthRatio.toFloat() / imageFormat.heightRatio.toFloat()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        IconButton(
            onClick = {
                /* TODO: Share */
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .zIndex(1f)
                .background(
                    Color.Black.copy(alpha = 0.26f),
                    shape = RoundedCornerShape(percent = 50)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share Image",
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                /* TODO: Bookmark */
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .zIndex(1f)
                .background(
                    Color.Black.copy(alpha = 0.26f),
                    shape = RoundedCornerShape(percent = 50)
                )
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Add to Bookmarks Image",
                tint = Color.White
            )
        }

        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = "Selected Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
    }

}

@Composable
fun ShowLivePreviewImages(
    images: List<Bitmap>,
    imageFormat: ImageFormat,
    generationProgress: Int
) {
    val lastImage = images.lastOrNull() ?: return
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("generation_progress_anim.json")
    )

    //  val aspectRatio = imageFormat.widthRatio.toFloat() / imageFormat.heightRatio.toFloat()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
        ) {
            Image(
                bitmap = lastImage.asImageBitmap(),
                contentDescription = "Live Preview",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        }

        LottieAnimation(
            composition = composition,
            progress = generationProgress/100f
        )


        LinearProgressIndicator(progress = generationProgress / 100f,)
        Text(text = "$generationProgress%", color = Color.White)
    }

}

@Composable
fun ThumbnailsOfImagesRow(
    images: List<Bitmap>,
    selectedImageIndex: Int,
    onThumbnailClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(images.size) { index ->
            /*  val image = images[index]
              val bitmap = decodeByteArrayToImageBitmap(image.data)
              if (bitmap != null) {*/
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(4.dp)
                    .clickable { onThumbnailClick(index) }
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    bitmap = images[index].asImageBitmap(),
                    contentDescription = "Thumbnail Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (index == selectedImageIndex) 2.dp else 0.dp,
                            color = if (index == selectedImageIndex) Purple40 else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Fit
                )

            }
        }
    }
}


@Composable
fun GenerationLoading(
){
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("generation_loading_v3_anim.json")
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }

}

@Composable
fun ImproveImageButton(

){
    CardWithAnimatedBorder(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        borderColors = listOf(Color.Cyan, Purple40, Color.Blue, Color.Magenta, Color.Yellow),
        externalShape = RoundedCornerShape(4.dp),
        innerShape = RoundedCornerShape(4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple40, Purple80),
                )
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = (stringResource(R.string.improve_image)),
                color = Color.Cyan,
                fontSize = 20.sp,
            )
        }
    }
}

