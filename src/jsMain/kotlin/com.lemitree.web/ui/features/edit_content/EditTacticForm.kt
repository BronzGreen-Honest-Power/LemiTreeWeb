package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.LemiButton
import com.lemitree.web.ui.components.LemiTextField
import com.lemitree.web.ui.components.LemiFieldTitle
import com.lemitree.web.ui.components.asTextFieldState
import com.lemitree.web.ui.theme.LocalWindowSize

@Composable
fun EditTacticForm(
    tactic: Tactic = Tactic.EMPTY,
    selectedPath: String,
    onClickSubmit: (Tactic) -> Unit,
) {
    var newTactic by remember { mutableStateOf(tactic) }
    var metadata by remember { mutableStateOf(tactic.metadata) }
    var content by remember { mutableStateOf(tactic.content) }
    var contentText by content.title.asTextFieldState()
    remember(metadata, content) {
        newTactic = tactic.copy(
            path = selectedPath,
            content = content,
            metadata = metadata,
        )
    }
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
    Column {
        LemiFieldTitle(
            text = "Path:",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LemiTextField(
            value = TextFieldValue("$selectedPath/${newTactic.fileName}"),
            onValueChange = {},
            readOnly = true,
            modifier = textFieldModifier,
        )
        LemiFieldTitle(
            text = "Title:",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LemiTextField(
            value = contentText,
            onValueChange = {
                contentText = it
                content = content.copy(title = it.text)
            },
            modifier = textFieldModifier,
        )
        MetadataFields(
            data = metadata,
            onMetadataChanged = { metadata = it },
        )
        TacticContentFields(
            content = content,
            onTacticContentChanged = { content = it }
        )
        LemiButton(
            onClick = { onClickSubmit(newTactic) },
            modifier = textFieldModifier,
            text = "Submit"
        ) //todo invalid required fields to throw error
    }
}