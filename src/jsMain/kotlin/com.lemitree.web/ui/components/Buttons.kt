package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun LemiButton(
    text: String,
    onClicked: () -> Unit,
) {
    Button({
        onClick { onClicked() }
    }) {
        Text(text)
    }
}