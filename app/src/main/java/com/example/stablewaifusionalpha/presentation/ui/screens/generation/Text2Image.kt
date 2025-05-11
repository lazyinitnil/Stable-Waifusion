package com.example.stablewaifusionalpha.presentation.ui.screens.generation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.presentation.ui.theme.Shapes
import com.example.stablewaifusionalpha.presentation.ui.theme.backgroundVariant
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel

@Composable
fun Text2ImageSection(
    viewModel: GenerationViewModel,
){
    with(viewModel){
        var isDropDownExpanded by remember { mutableStateOf(false) }
        val showPromptTemplates = remember { mutableStateOf(false) }

        val positiveTextPrompt by positiveTextPromptState.collectAsState()
        val imageCount by imageCountState.collectAsState()
        val style by styleState.collectAsState()
        val quality by qualityState.collectAsState()
        val qualityList by qualityListState.collectAsState()
        val resolution by resolutionState.collectAsState()
        val imageFormat by imageFormatState.collectAsState()
        val promptTemplateList by promptTemplateListState.collectAsState()

        val onPromptChange: (String) -> Unit = { updatePositiveTextPrompt(it) }
        val onImageCountChange: (Int) -> Unit = { updateImageCount(it) }
        val onStyleChange: (Style) -> Unit = { updateStyle(it) }
        val onQualityChange: (Quality) -> Unit = { updateQuality(it) }
        val onResolutionChange: (ResolutionLevel) -> Unit = { updateResolution(it) }
        val onImageFormatChange: (ImageFormat) -> Unit = { updateImageFormat(it) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                text = stringResource(id = R.string.write_a_prompt),
                textAlign = TextAlign.Center
            )

            PromptInputField(
                prompt = positiveTextPrompt,
                onPromptChange = onPromptChange,
                onOpenTemplates = { showPromptTemplates.value = true }
            )

            /* Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Cyan.copy(alpha = 0.4f))
                        .clickable { showBottomSheet.value = true },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = "",
                        tint = Color.Cyan,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(text = "Prompt Helper",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
                val dropdownMenuAnchor = remember { mutableStateOf(Offset(0f, 0f)) }
                Box(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(horizontal = 2.dp)
                        .height(20.dp)
                        .clickable {
                            isDropDownExpanded = true
                            dropdownMenuAnchor.value = Offset(0f, 0f)
                        }
                        .background(Purple80),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Images count: $imageCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                if (isDropDownExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dropdownMenuAnchor.value.x.dp,
                                top = dropdownMenuAnchor.value.y.dp
                            )
                    ) {
                        DropdownMenu(
                            expanded = isDropDownExpanded,
                            onDismissRequest = { isDropDownExpanded = false },
                            modifier = Modifier
                                .background(Purple40)
                                .width(40.dp)
                        ) {
                            val list = listOf(1, 2, 3, 4)
                            list.forEach { item ->
                                DropdownMenuItem(
                                    onClick = {
                                        onBatchSizeChange(item)
                                        isDropDownExpanded = false
                                    }
                                ) {
                                    Text(text = item.toString())
                                }
                            }
                        }
                    }
                }
            }*/

            PromptTemplatesBottomSheet(
                showPromptTemplates,
                promptTemplateList = promptTemplateList,
                positiveTextPrompt,
                onPromptChange
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.image_count),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))

            ImageCountChips(
                imageCount = imageCount,
                onSelect = { onImageCountChange(it) }
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.quality),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))

            QualitySection(
                qualityList = qualityList,
                quality = quality,
                style = style,
                onQualityChange = onQualityChange,
                onStyleChange = onStyleChange,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.resolution_and_image_formats),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))

            ImageFormats(
                quality = quality,
                resolution = resolution,
                imageFormat = imageFormat,
                onResolutionChange = onResolutionChange,
                onImageFormatChange = onImageFormatChange,
                onMoreClick = {}
            )
        }
    }
}

@Composable
fun ImageCountChips(
    imageCount: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = (1..4).toList()

    LazyRow (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
       items(options) { count ->
            val isSelected = count == imageCount

            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surface,
                label = "chipBackground"
            )

            Surface(
                modifier = Modifier
                    .height(28.dp)
                    .width(64.dp)
                    .clickable { onSelect(count) },
                color = backgroundColor,
                tonalElevation = if (isSelected) 4.dp else 1.dp,
                shape = Shapes.small
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$count",
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

