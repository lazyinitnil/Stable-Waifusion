package com.example.stablewaifusionalpha.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stablewaifusionalpha.presentation.ui.theme.Purple40

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val density = LocalDensity.current

    Column {
        Divider(
            color = Color.DarkGray,
            thickness = 1.dp
        )
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 68.dp, max = 80.dp),
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            BottomItem.entries.forEach { item ->

                val isSelected = currentRoute == item.route
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1f,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = ""
                )

                val offsetY by animateDpAsState(
                    targetValue = if (isSelected) 4.dp else 0.dp,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = ""
                )

                val offsetYInPx = with(density) { offsetY.toPx() }

                BottomNavigationItem(
                    icon = {
                        Icon(item.icon, contentDescription = null)
                    },
                    label = {
                        Text(stringResource(item.title))
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        }
                    },
                    selectedContentColor = Purple40,
                    unselectedContentColor = Color.White,
                    modifier = Modifier.graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationY = offsetYInPx
                    )
                )
            }
        }
    }
}
