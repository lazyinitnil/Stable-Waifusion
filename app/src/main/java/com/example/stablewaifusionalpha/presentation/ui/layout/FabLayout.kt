package com.example.stablewaifusionalpha.presentation.ui.layout

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFab(
    isExpanded: Boolean,
    onFabClick: () -> Unit,
    actions: List<Pair<ImageVector, () -> Unit>>
) {

    Box(contentAlignment = Alignment.BottomCenter) {
        actions.forEachIndexed { index, pair ->
            val animation by animateFloatAsState(
                targetValue = if (isExpanded) 1f else 0f,
                animationSpec = tween(300 + index * 100)
            )

            if (animation > 0f) {
                val xOffset = when (index) {
                    0 -> (-72).dp
                    1 -> 0.dp
                    else -> 72.dp
                }

                val yOffset = when (index) {
                    0 -> (-48).dp
                    1 -> (-72).dp
                    else -> (-48).dp
                }


                FloatingActionButton(
                    onClick = pair.second,
                    modifier = Modifier
                        .offset(x = xOffset, y = yOffset * animation)
                        .alpha(animation)
                        .size(48.dp),
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Icon(pair.first, contentDescription = null, tint = MaterialTheme.colors.onSecondary)
                }
            }
        }

        FloatingActionButton(onClick = onFabClick) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = "Expand menu"
            )
        }
    }
}
