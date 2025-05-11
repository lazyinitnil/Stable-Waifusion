package com.example.stablewaifusionalpha.presentation.ui.screens.generation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stablewaifusionalpha.presentation.navigation.GenerationTab
import com.example.stablewaifusionalpha.presentation.viewmodel.GenerationViewModel

@Composable
fun GenerationTabRow(
    selectedTab: GenerationTab,
    onTabSelected: (GenerationTab) -> Unit
) {
    val tabs = GenerationTab.values()
    ScrollableTabRow(
        selectedTabIndex = tabs.indexOf(selectedTab),
        containerColor = MaterialTheme.colorScheme.surface,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabs.indexOf(selectedTab)]),
                color = MaterialTheme.colorScheme.primary
            )
        },
        divider = {}
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                text = {
                    Text(
                        text = stringResource(tab.titleRes),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (tab == selectedTab)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}

@Composable
fun GenerationTabContent(
    selectedTab: GenerationTab,
    viewModel: GenerationViewModel
) {
    when (selectedTab) {
        GenerationTab.TEXT2IMAGE -> {
            Text2ImageSection(
                viewModel = viewModel
            )
        }

        GenerationTab.IMAGE2IMAGE -> {
            // TODO: Реализовать image2image UI
            PlaceholderContent("Image2Image coming soon")
        }

     /*   GenerationTab.INPAINTING -> {
            // TODO: Реализовать inpainting UI
            PlaceholderContent("Inpainting coming soon")
        }*/
    }
}

@Composable
fun PlaceholderContent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}


