package com.lemitree.web.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

// The hardcoded values for the columns are just proportions. todo review

data class WindowSize(
    val height: Int, // Px
    val width: Int, // Px
) {
    val heightDp: Dp
        @Composable get() = height.toDp()
    val widthDp: Dp
        @Composable get() = width.toDp()
    val centerColWidth: Int
        get() = (width * 0.55).roundToInt()
    val leftColWidth: Int
        get() = (width * 0.25).roundToInt()
    val rightColWith: Int
        get() = (width * 0.20).roundToInt()
    val centerColWidthDp: Dp
        @Composable get() = centerColWidth.toDp()
    val leftColWidthDp: Dp
        @Composable get() = leftColWidth.toDp()
    val rightColWithDp: Dp
        @Composable get() = rightColWith.toDp()
}

@Composable
fun Int.toDp() = with(LocalDensity.current) { toDp() }

val LocalWindowSize = staticCompositionLocalOf { WindowSize(0, 0) }
