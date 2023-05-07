package com.lemitree.web.ui.features.edit_tactic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.common.data.Benefit
import com.lemitree.common.data.BulletPoint
import com.lemitree.common.data.Instruction
import com.lemitree.common.data.Source
import com.lemitree.common.data.TacticContent
import com.lemitree.web.ui.components.Column
import com.lemitree.web.ui.components.LemiMultilineTextField
import com.lemitree.web.ui.components.LemiTextField
import com.lemitree.web.ui.components.Row
import com.lemitree.web.ui.components.VariableSizeList
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text

@Composable
fun TacticContentFields(
    fieldWidth: Int,
    content: TacticContent,
    onTacticContentChanged: (TacticContent) -> Unit,
) {
    var intro by remember { mutableStateOf("") }
    var why by remember { mutableStateOf("") }
    var how by remember { mutableStateOf("") }
    ExternalResourcesFields(
        content = content,
        onInfographicChanged = { onTacticContentChanged(content.copy(infographicLink = it)) },
        onVideoChanged = { onTacticContentChanged(content.copy(videoLink = it)) },
        onAudioChanged = { onTacticContentChanged(content.copy(audioLink = it)) },
    )
    Text("Introduction:")
    LemiMultilineTextField(
        text = intro,
        onValueChanged = {
            intro = it
            onTacticContentChanged(content.copy(intro = intro))
        },
    ) {
        textFieldStyle()
    }
    Text("Why:")
    LemiMultilineTextField(
        text = why,
        onValueChanged = {
            why = it
            onTacticContentChanged(content.copy(why = why))
        },
    ) {
        textFieldStyle()
    }
    BenefitsFields(
        fieldWidth = fieldWidth,
        onValueChanged = { onTacticContentChanged(content.copy(benefits = it)) }
    )
    Text("How:")
    LemiMultilineTextField(
        text = how,
        onValueChanged = {
            how = it
            onTacticContentChanged(content.copy(how = how))
        },
    ) {
        textFieldStyle()
    }
    InstructionsFields(
        fieldWidth = fieldWidth,
        onValueChanged = { onTacticContentChanged(content.copy(instructions = it)) }
    )
    SourcesFields(
        fieldWidth = fieldWidth,
        onValueChanged = { onTacticContentChanged(content.copy(sources = it)) }
    )
}

@Composable
private fun BenefitsFields(
    fieldWidth: Int,
    onValueChanged: (List<Benefit>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Benefit?>>(emptyList()) }
    remember(fields) {
        onValueChanged(fields.filterNotNull())
    }
    VariableSizeList(
        title = "Benefits:",
        fields = fields,
        onValueChanged = { fields = it },
        fieldContent = { benefit, onFieldValueChanged ->
            LemiMultilineTextField(
                text = benefit,
                onValueChanged = { onFieldValueChanged(it) }
            ) {
                width(fieldWidth.px)
                textFieldStyle()
            }
        }
    )
}

@Composable
private fun InstructionsFields(
    fieldWidth: Int,
    onValueChanged: (List<Instruction>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Instruction?>>(emptyList()) }
    remember(fields) {
        val filteredSources = fields
            .filterNotNull()
            .filter { it.title.isNotEmpty() && it.text.isNotEmpty() }
        onValueChanged(filteredSources)
    }
    VariableSizeList(
        title = "Instructions:",
        fields = fields,
        onValueChanged = { fields = it },
        fieldContent = { instruction, onFieldValueChanged ->
            val newInstruction = instruction ?: Instruction.EMPTY
            Column {
                LemiMultilineTextField(
                    text = instruction?.title,
                    hint = "Title",
                    onValueChanged = { onFieldValueChanged(newInstruction.copy(title = it)) },
                    contentStyle = { width(fieldWidth.px) },
                )
                LemiMultilineTextField(
                    text = instruction?.text,
                    hint = "Description",
                    onValueChanged = { onFieldValueChanged(newInstruction.copy(text = it)) },
                    contentStyle = { width(fieldWidth.px) },
                )
                var bulletPoints by remember { mutableStateOf<List<BulletPoint?>>(emptyList()) }
                remember(bulletPoints) {
                    val filteredBulledPoints = bulletPoints
                        .filterNotNull()
                        .filter { it.isNotEmpty() }
                    onFieldValueChanged(newInstruction.copy(bulletPoints = filteredBulledPoints))
                }
                VariableSizeList(
                    title = "Bullet points:",
                    fields = bulletPoints,
                    onValueChanged = { bulletPoints = it },
                    fieldContent = { bulletPoint, onBPFieldValueChanged ->
                        LemiMultilineTextField(
                            text = bulletPoint,
                            onValueChanged = { onBPFieldValueChanged(it) },
                            contentStyle = { width(fieldWidth.px) },
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun SourcesFields(
    fieldWidth: Int,
    onValueChanged: (List<Source>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Source?>>(emptyList()) }
    remember(fields) {
        val filteredSources = fields
            .filterNotNull()
            .filter { it.title.isNotEmpty() }
        onValueChanged(filteredSources)
    }
    VariableSizeList(
        title = "Sources:",
        fields = fields,
        onValueChanged = { fields = it },
        fieldContent = { source, onFieldValueChanged ->
            val newSource = source ?: Source.EMPTY
            Row {
                LemiTextField(
                    text = source?.title,
                    hint = "Title",
                    onValueChanged = { onFieldValueChanged(newSource.copy(title = it)) },
                    contentStyle = { width(fieldWidth.px) },
                )
                LemiTextField(
                    text = source?.link,
                    hint = "Link",
                    onValueChanged = { onFieldValueChanged(newSource.copy(link = it)) },
                    contentStyle = { width(fieldWidth.px) },
                )
            }
        }
    )
}

@Composable
private fun ExternalResourcesFields(
    content: TacticContent,
    onInfographicChanged: (String) -> Unit,
    onVideoChanged: (String) -> Unit,
    onAudioChanged: (String) -> Unit,
) {
    var infographicLink by remember { mutableStateOf(content.infographicLink) }
    var videoLink by remember { mutableStateOf(content.videoLink) }
    var audioLink by remember { mutableStateOf(content.audioLink) }
    Text("Infographic:")
    LemiTextField(
        text = infographicLink,
        onValueChanged = {
            infographicLink = it
            onInfographicChanged(it)
        },
    ) {
        textFieldStyle()
    }
    Text("Video:")
    LemiTextField(
        text = videoLink,
        onValueChanged = {
            videoLink = it
            onVideoChanged(it)
        },
    ) {
        textFieldStyle()
    }
    Text("Audio:")
    LemiTextField(
        text = audioLink,
        onValueChanged = {
            audioLink = it
            onAudioChanged(it)
        },
    ) {
        textFieldStyle()
    }
}