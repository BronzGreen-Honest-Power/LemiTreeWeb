package com.lemitree.web.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun Placeholder(
    text: String,
) {
    Text(
        text = text,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.alpha(0.6F),
    )
}

@Composable
fun NumberOutlinedTextField(
    value: String,
    onValueChange: (Int) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.toInt()) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier,
        placeholder = {
            Placeholder(hint)
        }
    )
}

@Composable
fun LemiTextField(
    modifier: Modifier = Modifier,
    value: String?,
    hint: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value ?: "",
        placeholder = { Placeholder(hint) },
        onValueChange = onValueChange,
        modifier = modifier
            .pointerHoverIcon(PointerIcon.Text),
    )
}

@Composable
fun LemiMultilineTextField(
    modifier: Modifier = Modifier,
    value: String?,
    onValueChange: (String) -> Unit,
    hint: String = "",
    minLines: Int = 2,
) {
    OutlinedTextField(
        value = value ?: "",
        placeholder = { Placeholder(hint) },
        onValueChange = onValueChange,
        modifier = modifier
            .pointerHoverIcon(PointerIcon.Text),
        minLines = minLines,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.Black, //todo fix
        )
    )
}