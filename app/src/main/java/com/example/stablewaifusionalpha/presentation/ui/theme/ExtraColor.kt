package com.example.stablewaifusionalpha.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtraColor(
    val success: Color,
    val warning: Color,
    val disabled: Color,
    val shimmer: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val onGradient: Color,
    val backgroundVariant: Color,
    val info: Color,
    val onInfo: Color,
    val neutral: Color
)

val LocalExtraColors = staticCompositionLocalOf {
    ExtraColor(
        success = Color.Unspecified,
        warning = Color.Unspecified,
        disabled = Color.Unspecified,
        shimmer = Color.Unspecified,
        gradientStart = Color.Unspecified,
        gradientEnd = Color.Unspecified,
        onGradient = Color.Unspecified,
        backgroundVariant = Color.Unspecified,
        info = Color.Unspecified,
        onInfo = Color.Unspecified,
        neutral = Color.Unspecified
    )
}

val LightSuccess = Color(0xFF2E7D32)
val LightWarning = Color(0xFFFFA000)
val LightShimmer = Color(0xFFF3F3F9)
val LightGradientStart = Color(0xFF9D00FF)
val LightGradientEnd = Color(0xFF00B8D9)
val LightOnGradient = Color(0xFF121212)
val LightDisabled = Color(0xFFC0C0C0)
val LightBackgroundVariant = Color(0xFF9C7FC4)

val LightInfo = Color(0xFF2196F3)
val LightOnInfo = Color.White
val LightNeutral = Color(0xFFE5E5E5)

val DarkSuccess = Color(0xFF4ADE80)
val DarkWarning = Color(0xFFFBBF24)
val DarkShimmer = Color(0xFF1A1A24)
val DarkGradientStart = Color(0xFF6A00F4)
val DarkGradientEnd = Color(0xFF00F0FF)
val DarkOnGradient = Color(0xFFFFFFFF)
val DarkDisabled = Color(0xFF55556A)
val DarkBackgroundVariant = Color(0xFF0F0C29)

val DarkInfo = Color(0xFF64B5F6)
val DarkOnInfo = Color.Black
val DarkNeutral = Color(0xFF4A4A4A)
