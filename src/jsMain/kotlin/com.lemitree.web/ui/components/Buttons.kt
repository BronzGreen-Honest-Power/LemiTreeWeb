package com.lemitree.web.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LemiButton(
    modifier: Modifier = Modifier,
    text: String,
    onClicked: () -> Unit,
) {
    Button(
        onClick =  { onClicked() },
        modifier = modifier,
    ) {
        Text(text)
    }
}

@Composable
fun <T> LemiSwitch(
    options: Pair<SwitchButton<T>, SwitchButton<T>>,
    initial: T,
    onSwitchClicked: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selected by remember { mutableStateOf(initial) }
    val (firstBtnBg, secondBtnBg) = when (selected) {
        options.first.value -> Color.LightGray to Color.Gray
        options.second.value -> Color.Gray to Color.LightGray //todo change light gray to alice blue
        else -> Color.Gray to Color.Gray
    }
    Row {
        Button(
            onClick = {
                selected = options.first.value
                onSwitchClicked(options.first.value)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = firstBtnBg)
        ) {
            Text(options.first.text)
        }
        Button(
            onClick = {
                selected = options.second.value
                onSwitchClicked(options.second.value)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = secondBtnBg)
        ) {
            Text(options.second.text)
        }
    }
}

data class SwitchButton<T>(
    val value: T,
    val text: String,
)