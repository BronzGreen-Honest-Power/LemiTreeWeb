package com.lemitree.web.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// The hardcoded values for the columns are just proportions.
// Interestingly, Compose Web uses dp, but with px values, so no conversion is needed.
// This however can change in the future, so we should keep the "xyzDp" properties for now.

data class WindowSize(
    val height: Int, // Px
    val width: Int, // Px
) {
    val heightDp: Dp
        @Composable get() = height.dp
    val widthDp: Dp
        @Composable get() = width.dp
    val centerColWidth: Double // Px
        get() = (width * 0.57)
    val leftColWidth: Double // Px
        get() = (width * 0.23)
    val rightColWidth: Double // Px
        get() = (width * 0.20)
    val centerColWidthDp: Dp
        @Composable get() = centerColWidth.dp
    val leftColWidthDp: Dp
        @Composable get() = leftColWidth.dp
    val rightColWidthDp: Dp
        @Composable get() = rightColWidth.dp
    val topMenuHeight: Int
        @Composable get() = 80
    val topMenuHeightDp: Dp
        @Composable get() = topMenuHeight.dp
}

val LocalWindowSize = staticCompositionLocalOf { WindowSize(0, 0) }
