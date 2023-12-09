package com.lemitree.web.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import org.jetbrains.skiko.loadBytesFromPath

@Composable
fun LemiTreeTheme(content: @Composable () -> Unit) {
    var fontFamily by remember { mutableStateOf<FontFamily?>(null) }
    LaunchedEffect(Unit) {
        fontFamily = FontFamily(
            Font(
                identity = "Arial",
                data = loadBytesFromPath("arial.ttf")
            )
        )
    }
    MaterialTheme(
        colors = colors(),
        typography = fontFamily?.let { Typography(defaultFontFamily = it) } ?: MaterialTheme.typography,
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