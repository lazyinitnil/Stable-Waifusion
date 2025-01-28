package com.example.stablewaifusionalpha.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.core.ext.calculateDimensions
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.SubStyle
import com.example.stablewaifusionalpha.domain.model.remote.Text2Image
import com.example.stablewaifusionalpha.presentation.ui.common.CardWithAnimatedBorder
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple40
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple80
import com.example.stablewaifusionalpha.presentation.viewmodel.Text2ImageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TextToImageScreen(
    modifier: Modifier,
    viewModel: Text2ImageViewModel ,
    onGenerateClick: () -> Unit
) {
    val prompt by viewModel.promptState.collectAsState()
    val resolution by viewModel.resolutionState.collectAsState()
    val imageFormat by viewModel.imageFormatState.collectAsState()
    val batchSize by viewModel.batchSizeState.collectAsState()
    val qualityList by viewModel.qualitiesState.collectAsState()
    val quality by viewModel.qualityState.collectAsState()
    val style by viewModel.styleState.collectAsState()
    val subStyle by viewModel.subStyleState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), Arrangement.Center)
                { Text(
                    stringResource(id = R.string.text_to_image),
                    color = Color.White,
                    fontSize = 24.sp
                ) } },
                backgroundColor = Color.Black,
                )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(80.dp)
                ,
                content = {
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
                        GenerateButton(
                            onGenerate = { request ->
                                viewModel.text2Image(
                                    request,
                                    style,
                                    subStyle ?: SubStyle(),
                                    resolution,
                                    imageFormat
                                )
                                onGenerateClick()},
                            prompt = prompt,
                            batchSize = batchSize,
                        )
                    }
                }, backgroundColor = Color.Black
            )
        },
    content =  { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                GenerateImageSection(
                    prompt = prompt,
                    batchSize = batchSize,
                    viewModel = viewModel,
                    quality = quality,
                    style = style,
                    subStyle = subStyle,
                    qualityList = qualityList,
                    resolution = resolution,
                    imageFormat = imageFormat,
                    onPromptChange = { newPrompt -> viewModel.updatePrompt(newPrompt) },
                    onBatchSizeChange = { newBatchSize -> viewModel.updateBatchSize(newBatchSize) },
                    onQualityChange = {newQuality -> viewModel.updateQuality(newQuality) },
                    onStyleChange = { newMode -> viewModel.updateStyle(newMode) },
                    onSubStyleChange = { newSubStyle -> viewModel.updateSubStyle(newSubStyle ?: SubStyle()) },
                )
            }
        }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateImageSection(
    prompt: String,
    batchSize: Int,
    onPromptChange: (String) -> Unit,
    onBatchSizeChange: (Int) -> Unit,
    viewModel: Text2ImageViewModel,
    qualityList: List<Quality>,
    quality: Quality,
    subStyle: SubStyle?,
    style: Style,
    onQualityChange:(Quality) -> Unit,
    onStyleChange:(Style) -> Unit,
    onSubStyleChange:(SubStyle?) -> Unit,
    resolution: ResolutionLevel,
    imageFormat: ImageFormat
){
    var isDropDownExpanded by remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = Purple40,
                    text = stringResource(id = R.string.write_a_prompt),
                    fontSize = 16.sp)
            }

            OutlinedTextField(
                value = prompt,
                onValueChange = { onPromptChange(it)},
                label = { Box(
                    modifier = Modifier
                        .border(2.dp, Color.Cyan, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                        .background(Purple40)
                ) {
                    Text(
                        text = stringResource(id = R.string.prompt_placeholder),
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
                    )
                } },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Purple40),
                minLines = 5,
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Purple40,
                    cursorColor = Color.Cyan,
                    focusedContainerColor = Purple40
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Cyan.copy(alpha = 0.4f))
                        .clickable { showBottomSheet.value = true },
                    verticalAlignment = Alignment.CenterVertically
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

                    val dropdownMenuAnchor = remember { mutableStateOf(Offset(0f, 0f)) }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                isDropDownExpanded = true
                                dropdownMenuAnchor.value = Offset(0f, 0f)
                            }
                            .background(Purple80),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "$batchSize",
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
                }
            }

            PromptHelperBottomSheet(
                showBottomSheet,
                viewModel,
                prompt,
                onPromptChange
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center)
                {
                    Text(
                        text = stringResource(R.string.quality),
                        color = Purple40,
                        fontSize = 20.sp
                    )
                }
                QualitySection(
                    viewModel = viewModel,
                    qualityList = qualityList,
                    quality = quality,
                    style = style,
                    subStyle = subStyle,
                    onQualityChange = onQualityChange,
                    onStyleChange =  onStyleChange,
                    onSubStyleChange = onSubStyleChange
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.resolution_and_image_formats),
                    color = Purple40,
                    fontSize = 20.sp,
                )
            }

            ImageFormats(
                viewModel = viewModel,
                quality = quality,
                resolution = resolution,
                imageFormat = imageFormat
            )

    }
}

@Composable
fun GenerateButton(
    onGenerate: (Text2Image) -> Unit,
    prompt: String,
    batchSize: Int,
){
    CardWithAnimatedBorder(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onCardClick = {onGenerate(
            Text2Image(
            prompt = prompt,
            batchSize = batchSize
        )
        )},
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
                text = stringResource(id = R.string.generate),
                color = Color.Cyan,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun QualitySection(
    viewModel: Text2ImageViewModel,
    qualityList: List<Quality>,
    quality: Quality,
    style: Style,
    subStyle: SubStyle?,
    onQualityChange: (Quality) -> Unit,
    onStyleChange: (Style) -> Unit,
    onSubStyleChange: (SubStyle?) -> Unit,
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
                    viewModel.updateQuality(selectedQuality)
                    val initialStyle = selectedQuality.styles.first()

                    viewModel.updateStyle(initialStyle)
                    onQualityChange(selectedQuality)
                    onSubStyleChange(null)
                }
            }
        )

        Text(
            text = stringResource(R.string.style),
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        StyleCard(
            images = quality.styles.map { it.preview },
            titles = quality.styles.map { it.name },
            selectedIndex = quality.styles.indexOf(style),
            onCardClick = { index ->
                val selectedStyle = index?.let { quality.styles[it] }
                if (selectedStyle != style) {
                    selectedStyle?.let { onStyleChange(it) }
                    onSubStyleChange(selectedStyle?.subStyles?.firstOrNull())
                }
            }
        )

        if (subStyle != null){
            Text(
                text = stringResource(R.string.sub_style),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        style.let { style ->
            StyleCard(
                images = style.subStyles.map { it.preview },
                titles = style.subStyles.map { it.name },
                selectedIndex = style.subStyles.indexOf(subStyle),
                onCardClick = { index ->
                    val selectedSubStyle = index?.let { style.subStyles[it] }
                    if (subStyle != selectedSubStyle) {
                        onSubStyleChange(selectedSubStyle)
                    }
                }
            )
        }
    }
}

@Composable
fun StyleCard(
    images: List<Int>,
    selectedIndex: Int?,
    titles: List<String>,
    onCardClick:(Int?) -> Unit
){
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp),
    ) {
        itemsIndexed(images) { index, drawableId ->
            val isSelected = index == selectedIndex
            Column(
                modifier = Modifier
                    .height(120.dp)
                    .width(90.dp)
                    .padding(4.dp)
                    .clickable {
                        onCardClick(
                            if (isSelected) null else index
                        )
                    }
            ) {
                    Image(
                        painter = painterResource(drawableId),
                        contentDescription = "",
                        modifier = Modifier
                            .width(120.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) Color.Blue else Color.Transparent,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .basicMarquee(
                                iterations = Int.MAX_VALUE
                            ),
                        text = titles.getOrElse(index) { "" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
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
            .height(38.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
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
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        contentColor = if (selectedItemIndex == index)
                            MaterialTheme.colorScheme.scrim
                        else
                            MaterialTheme.colorScheme.onSecondary
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
                                    MaterialTheme.colorScheme.scrim
                                else
                                    MaterialTheme.colorScheme.onSecondary
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
    viewModel: Text2ImageViewModel,
    quality: Quality,
    resolution: ResolutionLevel,
    imageFormat: ImageFormat
){

    val resolutions = quality.resolutions
    val imageFormats = quality.imageFormats

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            SegmentedControl(
                items = resolutions.map { it.name },
                selectedItemIndex = resolutions.indexOfFirst { it == resolution},
                onItemSelection = { selectedItemIndex ->
                    val selectedResolution = resolutions[selectedItemIndex]
                    if (selectedResolution != resolution) {
                        viewModel.updateResolution(selectedResolution)
                    }
                }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            imageFormats.forEach { selectedImageFormat: ImageFormat ->
                val isSelected = imageFormat == selectedImageFormat

                val (width, height) = calculateDimensions(resolution, selectedImageFormat)

                ImageFormatItem(
                    imageFormatLabel = selectedImageFormat.name,
                    imageFormatCardWidth = 64.dp,
                    imageFormatCardHeight = (64.dp * height / width),
                    isSelectedFormat = isSelected,
                    onImageFormatClick = {
                        viewModel.updateImageFormat(selectedImageFormat)
                    }
                )
            }
        }

    }
}

@Composable
fun ImageFormatItem(
    imageFormatLabel: String,
    imageFormatCardWidth: Dp,
    imageFormatCardHeight: Dp,
    isSelectedFormat: Boolean,
    onImageFormatClick: () -> Unit
){

    Column {
        Box(
            modifier = Modifier
                .size(88.dp)
                .background(Purple40)
                .then(
                    if (isSelectedFormat) Modifier.border(
                        2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(2.dp)
                    ) else Modifier
                ), contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .width(imageFormatCardWidth)
                    .height(imageFormatCardHeight)
                    .background(Color.DarkGray)
                    .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(2.dp))
                    .clickable { onImageFormatClick() }
            )
        }
        Text(
            text = imageFormatLabel,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptHelperBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    viewModel: Text2ImageViewModel,
    prompt: String,
    onPromptChange: (String) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val promptTemplates by viewModel.promptTemplatesState.collectAsState()
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
            containerColor = Color.DarkGray,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape( 50 ))
                        .background(Color.White)

                )
            },
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        ) {
            var selectedTabIndex by remember { mutableStateOf(0) }

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
                        readOnly = true
                    )

                }

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = Color.DarkGray,
                    edgePadding = 4.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color.Cyan
                        )
                    }
                ) {
                    promptTemplates.forEachIndexed { index, template ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index},
                            text = { Text(template.name, color = Color.White) }
                        )
                    }
                }
                PromptsTab(
                    prompts = promptTemplates[selectedTabIndex].prompts,
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


@OptIn(ExperimentalLayoutApi::class)
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
                    color = if (selectedPrompts.contains(prompt)) Color.Cyan else Color.White,
                    background = if (selectedPrompts.contains(prompt)) Color.DarkGray.copy(alpha = 0.8f) else Color.DarkGray.copy(
                        alpha = 0.5f
                    ),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onPromptClick(prompt) }
            )
        }
    }
}