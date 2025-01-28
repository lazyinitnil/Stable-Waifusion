package com.example.stablewaifusionalpha.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.stablewaifusionalpha.R

enum class BottomItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Default.Home, ROUTES.HOME_ROUTE),
    GALLERY(R.string.gallery, Icons.Default.PhotoLibrary, ROUTES.GALLERY_ROUTE)
}
