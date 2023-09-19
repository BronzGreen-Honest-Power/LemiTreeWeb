package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.Benefit
import com.lemitree.common.data.BulletPoint
import com.lemitree.common.data.Instruction
import com.lemitree.common.data.Source
import com.lemitree.common.data.TacticContent
import com.lemitree.web.ui.components.LemiTextField
import com.lemitree.web.ui.components.VariableSizeList
import com.lemitree.web.ui.components.asTextFieldState

@Composable
fun TacticContentFields(
    fieldWidth: Int,
    content: TacticContent,
    onTacticContentChanged: (TacticContent) -> Unit,
) {
    var intro by content.intro.asTextFieldState()
    var why by content.why.asTextFieldState()
    var how by content.how.asTextFieldState()
    ExternalResourcesFields(
        content = content,
        onInfographicChanged = { onTacticContentChanged(content.copy(infographicLink = it)) },
        onVideoChanged = { onTacticContentChanged(content.copy(videoLink = it)) },
        onAudioChanged = { onTacticContentChanged(content.copy(audioLink = it)) },
    )
    Text("Introduction:")
    LemiTextField(
        value = intro,
        minLines = 2,
        onValueChange = {
            intro = it
            onTacticContentChanged(content.copy(intro = intro.text))
        },
    )
    Text("Why:")
    LemiTextField(
        value = why,
        minLines = 2,
        onValueChange = {
            why = it
            onTacticContentChanged(content.copy(why = why.text))
        },
    )
    BenefitsFields(
        value = content.benefits,
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(benefits = it)) }
    )
    Text("How:")
    LemiTextField(
        value = how,
        minLines = 2,
        onValueChange = {
            how = it
            onTacticContentChanged(content.copy(how = how.text))
        },
    )
    InstructionsFields(
        value = content.instructions,
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(instructions = it)) }
    )
    SourcesFields(
        value = content.sources,
        fieldWidth = fieldWidth,
        onValueChange = { onTacticContentChanged(content.copy(sources = it)) }
    )
}

@Composable
private fun BenefitsFields(
    value: List<Benefit>,
    fieldWidth: Int,
    onValueChange: (List<Benefit>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Benefit?>>(value) }
    remember(fields) {
        onValueChange(fields.filterNotNull())
    }
    VariableSizeList(
        title = "Benefits:",
        fields = fields,
        onValueChange = { fields = it },
        fieldContent = { benefit, onFieldValueChanged ->
            var benefitText by benefit.asTextFieldState()
            LemiTextField(
                value = benefitText,
                minLines = 2,
                onValueChange = {
                    benefitText = it
                    onFieldValueChanged(it.text)
                },
                modifier = Modifier.width(fieldWidth.dp)
            )
        }
    )
}

@Composable
private fun InstructionsFields(
    value: List<Instruction>,
    fieldWidth: Int,
    onValueChange: (List<Instruction>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Instruction?>>(value) }
    remember(fields) {
        val filteredSources = fields
            .filterNotNull()
            .filter { it.title.isNotEmpty() }
        onValueChange(filteredSources)
    }
    VariableSizeList(
        title = "Instructions:",
        fields = fields,
        onValueChange = { fields = it },
        fieldContent = { instruction, onFieldValueChanged ->
            val newInstruction = instruction ?: Instruction.EMPTY
            Column {
                var instructionTitle by newInstruction.title.asTextFieldState()
                var instructionText by newInstruction.text.asTextFieldState()
                LemiTextField(
                    value = instructionTitle,
                    minLines = 2,
                    hint = "Title",
                    onValueChange = {
                        instructionTitle = it
                        onFieldValueChanged(newInstruction.copy(title = it.text))
                    },
                    modifier = Modifier.width(fieldWidth.dp),
                )
                LemiTextField(
                    value = instructionText,
                    minLines = 2,
                    hint = "Description",
                    onValueChange = {
                        instructionText = it
                        onFieldValueChanged(newInstruction.copy(text = it.text))
                    },
                    modifier = Modifier.width(fieldWidth.dp),
                )
                var bulletPoints by remember {
                    mutableStateOf<List<BulletPoint?>>(newInstruction.bulletPoints)
                }
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
                    fieldContent = { bulletPoint, onFieldValueChanged ->
                        var bulletPointText by remember { mutableStateOf(TextFieldValue(bulletPoint ?: "")) }
                        LemiTextField(
                            value = bulletPointText,
                            minLines = 2,
                            onValueChange = {
                                bulletPointText = it
                                onFieldValueChanged(it.text)
                            },
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
    value: List<Source>,
    fieldWidth: Int,
    onValueChange: (List<Source>) -> Unit,
) {
    var fields by remember { mutableStateOf<List<Source?>>(value) }
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
                var sourceTitle by source?.title.asTextFieldState()
                var sourceLink by source?.link.asTextFieldState()
                LemiTextField(
                    value = sourceTitle,
                    hint = "Title",
                    onValueChange = {
                        sourceTitle = it
                        onFieldValueChanged(newSource.copy(title = it.text))
                    },
                    modifier = Modifier.width(fieldWidth.dp)
                )
                LemiTextField(
                    value = sourceLink,
                    hint = "Link",
                    onValueChange = {
                        sourceLink = it
                        onFieldValueChanged(newSource.copy(link = it.text))
                    },
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
    var infographicLink by content.infographicLink.asTextFieldState()
    var videoLink by content.videoLink.asTextFieldState()
    var audioLink by content.audioLink.asTextFieldState()
    Text("Infographic:")
    LemiTextField(
        value = infographicLink,
        onValueChange = {
            infographicLink = it
            onInfographicChanged(it.text)
        }
    )
    Text("Video:")
    LemiTextField(
        value = videoLink,
        onValueChange = {
            videoLink = it
            onVideoChanged(it.text)
        },
    )
    Text("Audio:")
    LemiTextField(
        value = audioLink,
        onValueChange = {
            audioLink = it
            onAudioChanged(it.text)
        },
    )
}