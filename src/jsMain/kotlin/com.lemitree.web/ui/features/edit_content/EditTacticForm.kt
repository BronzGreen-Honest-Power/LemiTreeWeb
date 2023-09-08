package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.LemiTextField

const val columnWidth = 800
val textFieldModifier = Modifier
    .fillMaxWidth()
    .padding(10.dp)

@Composable
fun EditTacticForm(
    tactic: Tactic = Tactic.EMPTY,
    selectedPath: String,
    onClickSubmit: (Tactic) -> Unit,
) {
    var newTactic by remember { mutableStateOf(tactic) }
    var metadata by remember { mutableStateOf(tactic.metadata) }
    var content by remember { mutableStateOf(tactic.content) }
    var contentText by remember { mutableStateOf(TextFieldValue(content.title)) }
    remember(metadata, content) {
        newTactic = tactic.copy(
            path = selectedPath,
            content = content,
            metadata = metadata,
        )
    }
    Column {
        Text("Path:")
        LemiTextField(
            value = TextFieldValue("$selectedPath/${newTactic.fileName}"),
            onValueChange = {},
            readOnly = true,
            modifier = textFieldModifier,
        )
        Text("Title:")
        LemiTextField(
            value = contentText,
            onValueChange = {
                contentText = it
                content = content.copy(title = it.text)
            },
            modifier = textFieldModifier,
        )
        MetadataFields(
            fieldWidth = columnWidth / 2,
            data = metadata,
            onMetadataChanged = { metadata = it },
        )
        TacticContentFields(
            fieldWidth = columnWidth / 2,
            content = content,
            onTacticContentChanged = { content = it }
        )
        Button(
            onClick = { onClickSubmit(newTactic) },
            modifier = textFieldModifier,
        ) { //todo invalid required fields to throw error
            Text("Create tactic")
        }
    }
}