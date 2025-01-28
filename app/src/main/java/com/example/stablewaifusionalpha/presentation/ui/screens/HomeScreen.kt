package com.example.stablewaifusionalpha.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.presentation.ui.common.CardWithAnimatedBorder
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple40

@Composable
fun HomeScreen(
    context: Context,
    modifier: Modifier,
    onText2ImageCardClick: () -> Unit,
    onImage2ImageCardClick: () -> Unit,
) {
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

}

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
}