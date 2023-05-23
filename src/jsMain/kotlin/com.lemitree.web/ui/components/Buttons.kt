package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun LemiButton(
    text: String,
    contentStyle: StyleScope.() -> Unit = {},
    onClicked: () -> Unit,
) {
    Button({
        onClick { onClicked() }
        style { contentStyle() }
    }) {
        Text(text)
    }
}

data class SwitchButton<T>(
    val value: T,
    val text: String,
)

@Composable
fun <T> Switch(
    options: Pair<SwitchButton<T>, SwitchButton<T>>,
    initial: T,
    onSwitchClicked: (T) -> Unit,
    contentStyle: StyleScope.() -> Unit,
) {
    Row(
        rowStyle = {
            contentStyle()
        }
    ) {
        var selected by remember { mutableStateOf(initial) }
        val (firstBtnBg, secondBtnBg) = when (selected) {
            options.first.value -> Color.aliceblue to Color.gray
            options.second.value -> Color.gray to Color.aliceblue
            else -> Color.gray to Color.gray
        }

        LemiButton(
            text = options.first.text,
            onClicked = {
                selected = options.first.value
                onSwitchClicked(options.first.value)
            },
            contentStyle = {
                backgroundColor(firstBtnBg)
            }
        )
        LemiButton(
            text = options.second.text,
            onClicked = {
                selected = options.second.value
                onSwitchClicked(options.second.value)
            },
            contentStyle = {
                backgroundColor(secondBtnBg)
            }
        )
    }
}