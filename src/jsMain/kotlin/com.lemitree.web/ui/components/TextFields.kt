package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input

@Composable
fun LemiTextField(
    value: String?,
    onValueChanged: (String) -> Unit,
) {
    Div({ style { padding(25.px) } }) {
        Input(type = InputType.Text) {
            value(value ?: "")
            onInput { event -> onValueChanged(event.value) }
        }
    }
}