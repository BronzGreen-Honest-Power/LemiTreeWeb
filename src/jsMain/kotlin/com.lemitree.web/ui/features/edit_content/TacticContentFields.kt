package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.*
import com.lemitree.web.ui.components.LemiMultilineTextField
import com.lemitree.web.ui.components.LemiTextField
import com.lemitree.web.ui.components.VariableSizeList

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
        value = intro,
        onValueChange = {
            intro = it
            onTacticContentChanged(content.copy(intro = intro))
        },
    )
    Text("Why:")
    LemiMultilineTextField(
        value = why,
        onValueChange = {
            why = it
            onTacticContentChanged(content.copy(why = why))
        },
    )
    BenefitsFields(
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(benefits = it)) }
    )
    Text("How:")
    LemiMultilineTextField(
        value = how,
        onValueChange = {
            how = it
            onTacticContentChanged(content.copy(how = how))
        },
    )
    InstructionsFields(
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(instructions = it)) }
    )
    SourcesFields(
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(sources = it)) }
    )
}

@Composable
private fun BenefitsFields(
    fieldWidth: Int,
    onValueChange: (List<Benefit>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Benefit?>>(emptyList()) }
    remember(fields) {
        onValueChange(fields.filterNotNull())
    }
    VariableSizeList(
        title = "Benefits:",
        fields = fields,
        onValueChange = { fields = it },
        fieldContent = { benefit, onFieldValueChanged ->
            LemiMultilineTextField(
                value = benefit,
                onValueChange = { onFieldValueChanged(it) },
                modifier = Modifier.width(fieldWidth.dp)
            )
        }
    )
}

@Composable
private fun InstructionsFields(
    fieldWidth: Int,
    onValueChange: (List<Instruction>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Instruction?>>(emptyList()) }
    remember(fields) {
        val filteredSources = fields
            .filterNotNull()
            .filter { it.title.isNotEmpty() && it.text.isNotEmpty() }
        onValueChange(filteredSources)
    }
    VariableSizeList(
        title = "Instructions:",
        fields = fields,
        onValueChange = { fields = it },
        fieldContent = { instruction, onFieldValueChanged ->
            val newInstruction = instruction ?: Instruction.EMPTY
            Column {
                LemiMultilineTextField(
                    value = instruction?.title,
                    hint = "Title",
                    onValueChange = { onFieldValueChanged(newInstruction.copy(title = it)) },
                    modifier = Modifier.width(fieldWidth.dp),
                )
                LemiMultilineTextField(
                    value = instruction?.text,
                    hint = "Description",
                    onValueChange = { onFieldValueChanged(newInstruction.copy(text = it)) },
                    modifier = Modifier.width(fieldWidth.dp),
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
                    onValueChange = { bulletPoints = it },
                    fieldContent = { bulletPoint, onBPFieldValueChanged ->
                        LemiMultilineTextField(
                            value = bulletPoint,
                            onValueChange = { onBPFieldValueChanged(it) },
                            modifier = Modifier.width(fieldWidth.dp),
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
    onValueChange: (List<Source>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Source?>>(emptyList()) }
    remember(fields) {
        val filteredSources = fields
            .filterNotNull()
            .filter { it.title.isNotEmpty() }
        onValueChange(filteredSources)
    }
    VariableSizeList( //todo
        title = "Sources:",
        fields = fields,
        onValueChange = { fields = it },
        fieldContent = { source, onFieldValueChanged ->
            val newSource = source ?: Source.EMPTY
            Row {
                LemiTextField(
                    value = source?.title,
                    hint = "Title",
                    onValueChange = { onFieldValueChanged(newSource.copy(title = it)) },
                    modifier = Modifier.width(fieldWidth.dp)
                )
                LemiTextField(
                    value = source?.link,
                    hint = "Link",
                    onValueChange = { onFieldValueChanged(newSource.copy(link = it)) },
                    modifier = Modifier.width(fieldWidth.dp)
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
        value = infographicLink,
        onValueChange = {
            infographicLink = it
            onInfographicChanged(it)
        }
    )
    Text("Video:")
    LemiTextField(
        value = videoLink,
        onValueChange = {
            videoLink = it
            onVideoChanged(it)
        },
    )
    Text("Audio:")
    LemiTextField(
        value = audioLink,
        onValueChange = {
            audioLink = it
            onAudioChanged(it)
        },
    )
}