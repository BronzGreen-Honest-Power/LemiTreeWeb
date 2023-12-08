package com.lemitree.web.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.browser.window
import kotlinx.coroutines.await

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
fun LemiFieldTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}

@Composable
fun NumberOutlinedTextField(
    value: String,
    onValueChange: (Int?) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            if (text.isEmpty()) onValueChange(null)
            text.toIntOrNull()?.let {
                onValueChange(it)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
        modifier = modifier,
        placeholder = {
            Placeholder(hint)
        }
    )
}

@Composable
fun LemiTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    hint: String = "",
    readOnly: Boolean = false,
    minLines: Int = 1,
    onValueChange: (TextFieldValue) -> Unit,
) {
    var shortcutEvent: ShortcutEvent? by remember { mutableStateOf(null) }
    var isCtrlPressed by remember { mutableStateOf(false) }

    LaunchedEffect(shortcutEvent) {
        ShortcutEventHandler(
            shortcutEvent = shortcutEvent,
            value = value,
            onValueChange = onValueChange,
        )
        shortcutEvent = null
    }
    Column(
        modifier = Modifier
            .onPreviewKeyEvent {
                isCtrlPressed = it.isCtrlPressed
                shortcutEvent = it.filterDown()?.toShortcutEvent()
                false
            }
    ) {
        OutlinedTextField(
            value = value,
            placeholder = { Placeholder(hint) },
            onValueChange = {
                if (!isCtrlPressed) onValueChange(it)
            },
            readOnly = readOnly,
            minLines = minLines,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = modifier
                .pointerHoverIcon(PointerIcon.Text)
        )
    }
}

enum class ShortcutEvent {
    CUT, COPY, PASTE, HIGHLIGHT_ALL
}

private suspend fun ShortcutEventHandler(
    shortcutEvent: ShortcutEvent?,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
//    clipboardManager: ClipboardManager = getKoinInstance()
) {
    shortcutEvent ?: return
    when (shortcutEvent) {
        ShortcutEvent.CUT -> {
            // The cut function seems to work same as copy, so we clear the selected value.
            onValueChange(value.replaceSelected(""))
        }
        ShortcutEvent.COPY -> {
            // Unused - seems to work out of the box, but keeping the code below for future reference.
//            window.navigator.clipboard.writeText(value.selectedText).await()
//            clipboardManager.setText(value.selectedText)
        }
        ShortcutEvent.PASTE -> {
            val clipboardText = window.navigator.clipboard.readText().await()
            onValueChange(value.replaceSelected(clipboardText))
        }
        ShortcutEvent.HIGHLIGHT_ALL -> {
            onValueChange(value.copy(selection = TextRange(0, value.text.length)))
        }
    }
}

private fun KeyEvent.filterDown() =
    if (type == KeyEventType.KeyDown) this else null

private fun KeyEvent.toShortcutEvent() = when {
    isCtrlPressed && key == Key.X -> ShortcutEvent.CUT
    isCtrlPressed && key == Key.V -> ShortcutEvent.PASTE
    isCtrlPressed && key == Key.A -> ShortcutEvent.HIGHLIGHT_ALL
    else -> null
}

private fun TextFieldValue.replaceSelected(replacement: String) =
    copy(text = text.replaceRange(selection.min, selection.max, replacement), TextRange(0, 0))

@Composable
fun String?.asTextFieldState() = remember { mutableStateOf(TextFieldValue(this ?: "")) }
