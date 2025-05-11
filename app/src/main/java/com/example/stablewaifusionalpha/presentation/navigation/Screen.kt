package com.example.stablewaifusionalpha.presentation.navigation

import androidx.annotation.StringRes
import com.example.stablewaifusionalpha.R

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Welcome : Screen("welcome")

    data object Hub: Screen("hub")
    data object Generation: Screen("generation")
    data object GenerationResult: Screen("generation_result")
    data object Gallery: Screen("gallery")

    data class ImageDetail(val id: String) : Screen("imageDetail/$id") {
        companion object {
            private const val BASE_ROUTE = "imageDetail"
            fun createRoute(id: String) = "$BASE_ROUTE/$id"
        }
    }

}

enum class GenerationTab(@StringRes val titleRes: Int) {
    TEXT2IMAGE(R.string.text_to_image),
    IMAGE2IMAGE(R.string.image_to_image),
   // INPAINTING(R.string.inpainting)
}
