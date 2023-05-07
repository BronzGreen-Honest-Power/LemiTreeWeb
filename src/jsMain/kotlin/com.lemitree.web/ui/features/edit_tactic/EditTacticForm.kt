package com.lemitree.web.ui.features.edit_tactic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.Column
import com.lemitree.web.ui.components.LemiButton
import com.lemitree.web.ui.components.LemiTextField
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text

private const val columnWidth = 800
val textFieldStyle: StyleScope.() -> Unit = {
    marginTop(10.px)
    marginBottom(10.px)
    width(columnWidth.px)
}

@Composable
fun EditTacticForm(
    tactic: Tactic = Tactic.EMPTY,
    selectedPath: String,
    onClickSubmit: (Tactic) -> Unit,
) {
    var newTactic by remember { mutableStateOf(tactic) }
    var metadata by remember { mutableStateOf(tactic.metadata) }
    var content by remember { mutableStateOf(tactic.content) }
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
            text = "$selectedPath/${newTactic.fileName}",
            readonly = true,
        ) {
            textFieldStyle()
            cursor("pointer")
        }
        Text("Title:")
        LemiTextField(
            text = content.title,
            onValueChanged = { content = content.copy(title = it) },
        ) {
            textFieldStyle()
        }
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
        LemiButton(
            text = "Create tactic",
            contentStyle = {
                width(columnWidth.px)
                marginTop(10.px)
                marginBottom(10.px)
            }
        ) { //todo invalid required fields to throw error
            onClickSubmit(newTactic)
        }
    }
}