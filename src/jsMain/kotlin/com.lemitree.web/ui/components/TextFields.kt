package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.TextAreaWrap
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.readOnly
import org.jetbrains.compose.web.attributes.rows
import org.jetbrains.compose.web.attributes.wrap
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun LemiTextField(
    text: String?,
    onValueChanged: (String) -> Unit = {},
    readonly: Boolean = false,
    hint: String = "",
    onFieldClicked: () -> Unit = {},
    contentStyle: StyleScope.() -> Unit = {},
) {
    Div {
        Input(type = InputType.Text) {
            style {
                contentStyle()
            }
            placeholder(hint)
            value(text ?: "")
            onClick { onFieldClicked() }
            if (readonly) readOnly()
            else onInput { event -> onValueChanged(event.value) }
        }
    }
}

@Composable
fun LemiNumberTextField(
    text: String?,
    onValueChanged: (Number?) -> Unit,
    hint: String = "",
    contentStyle: StyleScope.() -> Unit = {},
) {
    Div {
        Input(type = InputType.Number) {
            style {
                contentStyle()
            }
            value(text ?: "")
            placeholder(hint)
            onInput { event -> onValueChanged(event.value) }
        }
    }
}

@Composable
fun LemiMultilineTextField(
    text: String?,
    onValueChanged: (String) -> Unit,
    hint: String = "",
    lines: Int = 2,
    contentStyle: StyleScope.() -> Unit = {},
) {
    Div {
        TextArea {
            style {
                contentStyle()
            }
            value(text ?: "")
            placeholder(hint)
            rows(lines)
            wrap(TextAreaWrap.Soft)
            onInput { event -> onValueChanged(event.value) }
        }
    }
}