package com.example.stablewaifusionalpha.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.stablewaifusionalpha.R

enum class BottomItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.hub, Icons.Default.Home, Screen.Hub.route),
    GALLERY(R.string.gallery, Icons.Default.PhotoLibrary, Screen.Gallery.route)
}

enum class BottomFabAction(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
){
    TEXT2IMAGE(R.string.text_to_image, Icons.Default.TextFields, Screen.Generation.route)
}