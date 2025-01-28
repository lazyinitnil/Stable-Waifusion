package com.example.stablewaifusionalpha.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.presentation.ui.common.CardWithAnimatedBorder
import com.example.stablewaifusionalpha.presentation.ui.common.StateHandler
import com.example.stablewaifusionalpha.presentation.ui.common.decodeBase64ToImageBitmap
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple40
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple80
import com.example.stablewaifusionalpha.presentation.viewmodel.GeneratedImagesViewModel
import com.example.stablewaifusionalpha.presentation.viewmodel.Text2ImageViewModel

@Composable
fun GeneratedImagesScreen(
    modifier: Modifier,
    viewModel: Text2ImageViewModel,
    generatedImagesViewModel: GeneratedImagesViewModel = viewModel()
){

    val state by viewModel.text2ImageState.collectAsState()
    val imageFormat by viewModel.imageFormatState.collectAsState()
    val imagesCount by viewModel.batchSizeState.collectAsState()
    /*val screenHeight = LocalConfiguration.current.screenHeightDp.dp*/
    var selectedImageIndex by remember { mutableStateOf(0) }

    val isGenerationLoading by generatedImagesViewModel.isGenerationLoadingState.collectAsState()
    val isGenerationComplete by generatedImagesViewModel.isGenerationCompleteState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(80.dp)
                ,
                content = {
                    if (isGenerationComplete){
                    Row (
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
                    }
                }, backgroundColor = Color.Black
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    StateHandler(
                        state = state,
                        onEmpty = {},
                        onError = {},
                        onLoading = {
                            GenerationLoading(isGenerationLoading, isGenerationComplete)
                        },
                        onSuccess = { newImages ->
                            generatedImagesViewModel.updateIsGenerationLoading(false)
                            generatedImagesViewModel.updateIsGenerationComplete(true)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                ImagesContent(
                                    images = newImages.images,
                                    selectedImageIndex = selectedImageIndex,
                                    imageFormat
                                )
                            }
                            if (imagesCount != 1) {
                                ThumbnailsOfImagesRow(
                                    images = newImages.images,
                                    selectedImageIndex = selectedImageIndex,
                                    onThumbnailClick = { index -> selectedImageIndex = index },
                                )
                            }
                        }
                    )
                    if (isGenerationComplete) {
                        Column(
                            modifier = Modifier
                                .weight(0.25f)
                                .background(Purple40)
                        ) {

                        }
                    }
                }
            }
        }
    )
}


@Composable
fun ImagesContent(
    images: List<String>,
    selectedImageIndex: Int,
    imageFormat: ImageFormat
) {
    var isFullScreen by remember { mutableStateOf(false) }
    var fullScreenImage by remember { mutableStateOf("") }

    if (images.isEmpty()) {
        Text(
            text = "No images to display",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = Color.Gray
        )
    } else {
        val selectedImage = images[selectedImageIndex]
        val bitmap = decodeBase64ToImageBitmap(selectedImage)
        val aspectRatio = imageFormat.widthRatio.toFloat() / imageFormat.heightRatio.toFloat()

        if (bitmap != null) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = {

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
                    bitmap = bitmap,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio)
                        .clickable {
                            isFullScreen = true
                            fullScreenImage = selectedImage
                        },
                    contentScale = ContentScale.Fit
                )
            }
        } else {
            Text(
                text = "Error decoding image",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            )
        }
        if (isFullScreen) {
            FullScreenImageDialog(
                image = fullScreenImage,
                onDismiss = { isFullScreen = false }
            )
        }

    }
}


@Composable
fun FullScreenImageDialog(image: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() }
        ) {
            val bitmap = decodeBase64ToImageBitmap(image)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "Full Screen Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun ThumbnailsOfImagesRow(
    images: List<String>,
    selectedImageIndex: Int,
    onThumbnailClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(images) { base64Image ->
            val bitmap = decodeBase64ToImageBitmap(base64Image)
            val imageIndex = images.indexOf(base64Image)
            if (bitmap != null) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                        .clickable { onThumbnailClick(images.indexOf(base64Image)) }
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Thumbnail Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = if (imageIndex == selectedImageIndex) 2.dp else 0.dp,
                                color = if (imageIndex == selectedImageIndex) Purple40 else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun GenerationLoading(
    isGenerationLoading: Boolean,
    isGenerationComplete: Boolean
){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.Asset("generation_loading_v3_anim.json")
        )

        LottieAnimation(
            composition = composition,
            isPlaying = isGenerationLoading,
            iterations = if (isGenerationComplete) 1 else LottieConstants.IterateForever
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