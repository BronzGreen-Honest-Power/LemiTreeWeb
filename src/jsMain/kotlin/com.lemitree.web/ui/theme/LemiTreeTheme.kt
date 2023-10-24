package com.lemitree.web.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LemiTreeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colors(),
        content = content,
    )
}

fun colors() = lightColors(
    primary = Color(0xFF09207E),
    onPrimary = Color.White,
    secondary = Color(0xFF212B27),
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
)