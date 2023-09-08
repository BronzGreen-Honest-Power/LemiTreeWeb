package com.lemitree.web.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.substring
import com.lemitree.common.helpers.getKoinInstance
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.skiko.ClipboardManager

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
    readOnly: Boolean = false,
    minLines: Int = 1,
    onValueChange: (String) -> Unit,
) {
    var textValue by remember { mutableStateOf(TextFieldValue(text = value ?: "")) }
    var pasteText by remember { mutableStateOf(false) }
    LaunchedEffect(pasteText) {
        if (pasteText) {
            val clipboardText = window.navigator.clipboard.readText().await()
            val finalString = textValue.replaceSelectedText(clipboardText)
            onValueChange(finalString)
            pasteText = false
        }
    }
    OutlinedTextField(
        value = textValue,
        placeholder = { Placeholder(hint) },
        onValueChange = { textValue = it },
        readOnly = readOnly,
        minLines = minLines,
        modifier = modifier
            .pointerHoverIcon(PointerIcon.Text)
            .onPreviewKeyEvent {
                when {
                    it.isCtrlPressed && it.key == Key.C -> { //todo copying overwrites text with "c"
                        window.navigator.clipboard.writeText(textValue.selectedText)
                        true
                    }
                    it.isCtrlPressed && it.key == Key.V -> {
                        pasteText = true
                        false
                    }
                    else -> false
                }
            }
    )
}//todo remove

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

val TextFieldValue.selectedText
    get() = text.substring(selection)

private fun TextFieldValue.replaceSelectedText(replacement: String) =
    text.replaceRange(selection.start, selection.end, replacement)

private fun TextFieldValue.replaceSelected(replacement: String) =
    copy(text = text.replaceRange(selection.min, selection.max, replacement), TextRange(0, 0))