package com.example.stablewaifusionalpha.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.stablewaifusionalpha.presentation.UIState

@Composable
fun <T> StateHandler(
    state: UIState<T>,
    onSuccess: @Composable (T) -> Unit,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String) -> Unit = { error ->
        Text(
            text = error,
            color = Color.Red,
            modifier = Modifier.fillMaxSize()
        )
    },
    onEmpty: @Composable () -> Unit = {
        Text(
            text = "No data available",
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        )
    }
) {
    when (state) {
        is UIState.Success -> onSuccess(state.data)
        is UIState.Loading -> onLoading()
        is UIState.Error -> onError(state.error)
        is UIState.Empty -> onEmpty()
    }
}
