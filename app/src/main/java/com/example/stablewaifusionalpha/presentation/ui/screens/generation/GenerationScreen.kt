package com.example.stablewaifusionalpha.presentation.ui.screens.generation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.core.utils.calculateDimensions
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.presentation.navigation.GenerationTab
import com.example.stablewaifusionalpha.presentation.ui.theme.Shapes
import com.example.stablewaifusionalpha.presentation.ui.theme.backgroundVariant
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerationScreen(
    viewModel: GenerationViewModel,
    onGenerateClick: () -> Unit
) {
    with(viewModel) {
        val positiveTextPrompt  by positiveTextPromptState.collectAsState()
        val resolution by resolutionState.collectAsState()
        val imageFormat by imageFormatState.collectAsState()
        val imageCount by imageCountState.collectAsState()
        val qualityList by qualityListState.collectAsState()
        val quality by qualityState.collectAsState()
        val style by styleState.collectAsState()

        val selectedTab = remember { mutableStateOf(GenerationTab.TEXT2IMAGE) }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(selectedTab.value.titleRes),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                GenerationTabRow(
                    selectedTab = selectedTab.value,
                    onTabSelected = { selectedTab.value = it }
                )
            },
            bottomBar = {
                Surface(
                    tonalElevation = 3.dp,
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.drawTopBorder()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                            GenerateButton(
                                onGenerate = {
                                    viewModel.sendPrompt(
                                        style,
                                        positiveTextPrompt,
                                        resolution,
                                        imageFormat,
                                        imageCount
                                    )
                                    onGenerateClick()
                                },
                            )
                        }
                    }

            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.backgroundVariant)
                            )
                        )
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        GenerationTabContent(selectedTab.value, viewModel)
                    }
                }
            }
        )
    }
}

@Composable
fun GenerateButton(
    onGenerate: () -> Unit,
){
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary)))
            .clickable { onGenerate() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(R.string.generate),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun QualitySection(
    qualityList: List<Quality>,
    quality: Quality,
    style: Style,
    onQualityChange: (Quality) -> Unit,
    onStyleChange: (Style) -> Unit,
) {
    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(isInitialized) {
        if (!isInitialized && qualityList.isNotEmpty()) {
            val initialQuality = qualityList[0]
            val initialStyle = initialQuality.styles.first()

            onQualityChange(initialQuality)
            onStyleChange(initialStyle)
            isInitialized = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SegmentedControl(
            items = qualityList.map { it.name },
            selectedItemIndex = qualityList.indexOf(quality),
            onItemSelection = { index ->
                val selectedQuality = qualityList[index]
                if (quality != selectedQuality) {
                    val initialStyle = selectedQuality.styles.first()

                    onStyleChange(initialStyle)
                    onQualityChange(selectedQuality)
                }
            }
        )

        Text(
            text = stringResource(R.string.styles),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth().padding(start = dimensionResource(R.dimen.padding_small)),
            textAlign = TextAlign.Start
        )

        StylesGrid(
            images = quality.styles.map { it.preview },
            titles = quality.styles.map { it.name },
            selectedIndex = quality.styles.indexOf(style),
            onStyleClick = { index ->
                val selectedStyle = index?.let { quality.styles[it] }
                if (selectedStyle != style) {
                    selectedStyle?.let { onStyleChange(it) }
                }
            }
        )

    }
}

@Composable
fun StylesGrid(
    images: List<Int>,
    selectedIndex: Int?,
    titles: List<String>,
    onStyleClick:(Int?) -> Unit
){
    val chunkedItems = images.zip(titles).withIndex().chunked(2)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(chunkedItems) { chunk ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                chunk.forEach { (index, pair) ->
                    val (drawableId, title) = pair
                    val isSelected = index == selectedIndex

                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .clip(Shapes.medium)
                            .clickable {
                                onStyleClick(
                                    if (isSelected) null else index
                                )
                            }
                    ) {
                        Image(
                            painter = painterResource(drawableId),
                            contentDescription = "",
                            modifier = Modifier
                                .aspectRatio(0.9f)
                                .clip(Shapes.medium)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = Shapes.medium
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee(iterations = Int.MAX_VALUE),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SegmentedControl(
    items: List<String>,
    selectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 24,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = {
                        onItemSelection(index)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedItemIndex == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        contentColor = if (selectedItemIndex == index)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = when (index) {
                        0 -> RoundedCornerShape(
                            topStartPercent = cornerRadius,
                            topEndPercent = cornerRadius,
                            bottomStartPercent = cornerRadius,
                            bottomEndPercent = cornerRadius
                        )

                        items.size - 1 -> RoundedCornerShape(
                            topStartPercent = cornerRadius,
                            topEndPercent = cornerRadius,
                            bottomStartPercent = cornerRadius,
                            bottomEndPercent = cornerRadius
                        )

                        else -> RoundedCornerShape(0)
                    },
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = item,
                            style = LocalTextStyle.current.copy(
                                fontSize = 14.sp,
                                fontWeight = if (selectedItemIndex == index)
                                    LocalTextStyle.current.fontWeight
                                else
                                    FontWeight.Normal,
                                color = if (selectedItemIndex == index)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            textAlign = TextAlign.Center,
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageFormats(
    quality: Quality,
    resolution: ResolutionLevel,
    imageFormat: ImageFormat,
    onResolutionChange: (ResolutionLevel) -> Unit,
    onImageFormatChange: (ImageFormat) -> Unit,
    onMoreClick: () -> Unit
){
    val resolutions = quality.resolutions
    val imageFormats = quality.imageFormats

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        SegmentedControl(
            items = resolutions.map { it.name },
            selectedItemIndex = resolutions.indexOfFirst { it == resolution},
            onItemSelection = { selectedItemIndex ->
                val selectedResolution = resolutions[selectedItemIndex]
                if (selectedResolution != resolution) {
                    onResolutionChange(selectedResolution)
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageFormats, key = { it.name }) { selectedImageFormat ->
                val (width, height) = calculateDimensions(resolution, selectedImageFormat)
                val aspectRatio = width.toFloat() / height.toFloat()
                val isSelected = selectedImageFormat == imageFormat

                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected)
                        MaterialTheme.colorScheme.tertiaryContainer
                    else
                        MaterialTheme.colorScheme.surface,
                    label = "backgroundColor"
                )


                Surface(
                    modifier = Modifier
                        .height(72.dp)
                        .width(72.dp * aspectRatio)
                        .clickable {
                            onImageFormatChange(selectedImageFormat)
                        },
                    shape = Shapes.medium,
                    color = backgroundColor,
                    tonalElevation = if (isSelected) 4.dp else 1.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                           // .aspectRatio(width.toFloat() / height)
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = selectedImageFormat.name,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .width(72.dp * 1.2f)
                        .height(72.dp)
                        .clickable { onMoreClick() },
                    contentAlignment = Alignment.Center
                ) {

                    Surface(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {

                            Icon(
                                Icons.Default.ExpandMore,
                                contentDescription = "More formats",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(2.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        tonalElevation = 4.dp
                    ) {}
                }
            }
        }
    }
}

@Composable
fun PromptInputField(
    prompt: String,
    onPromptChange: (String) -> Unit,
    onOpenTemplates: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = prompt,
            onValueChange = onPromptChange,
            label = { Text(stringResource(R.string.prompt)) },
            placeholder = { Text(stringResource(R.string.prompt_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 22.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onBackground,
                placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 6,
            singleLine = false,

        )

        TextButton(
            onClick = onOpenTemplates,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 8.dp)
        ) {
            Icon(Icons.Default.List, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource( R.string.prompt_templates))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptTemplatesBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    promptTemplateList: List<PromptTemplate>,
    prompt: String,
    onPromptChange: (String) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val selectedPrompts = remember { mutableStateListOf<String>() }

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) {
            selectedPrompts.clear()
            selectedPrompts.addAll(prompt.split(", ").filter { it.isNotBlank() })
        }
    }

    if (showBottomSheet.value){
        ModalBottomSheet(
            onDismissRequest = { coroutineScope.launch {
                sheetState.hide()
                showBottomSheet.value = false
            } },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape( 50 ))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                )
            },
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        ) {
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            Column(modifier = Modifier.fillMaxSize())
            {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    TextField(
                        value = selectedPrompts.joinToString(", "),
                        onValueChange = {onPromptChange(it)},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                }

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth(),
                    edgePadding = 4.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                ) {
                    promptTemplateList.forEachIndexed { index, template ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index},
                            text = {
                                Text(
                                    template.name,
                                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.tertiary
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }

                        )
                    }
                }
                PromptsTab(
                    prompts = promptTemplateList[selectedTabIndex].prompts,
                    selectedPrompts = selectedPrompts,
                    onPromptClick = { clickedPrompt ->
                        if (selectedPrompts.contains(clickedPrompt)) {
                            selectedPrompts.remove(clickedPrompt)
                        } else {
                            selectedPrompts.add(clickedPrompt)
                        }
                        onPromptChange(selectedPrompts.joinToString(", "))
                    }
                )
            }
        }
    }
}

@Composable
fun PromptsTab(
    prompts: List<String>,
    selectedPrompts: List<String>,
    onPromptClick: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        prompts.forEach { prompt ->
            Text(
                text = prompt,
                style = TextStyle(
                    color = if (selectedPrompts.contains(prompt))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface,
                    background = if (selectedPrompts.contains(prompt))
                        MaterialTheme.colorScheme.surfaceVariant
                    else
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onPromptClick(prompt) }
            )
        }
    }
}

@Composable
fun Modifier.drawTopBorder(color: Color = MaterialTheme.colorScheme.outline, thickness: Dp = 1.dp): Modifier =
    this.then(Modifier.drawBehind {
        val strokeWidth = thickness.toPx()
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth
        )
    }
    )
