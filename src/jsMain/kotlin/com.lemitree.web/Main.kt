package com.lemitree.web

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.web.ui.components.LemiSwitch
import com.lemitree.web.ui.components.SwitchButton
import com.lemitree.web.ui.features.edit_content.AddContent
import com.lemitree.web.ui.features.edit_content.EditTacticForm
import com.lemitree.web.ui.features.tree_view.TacticTree
import com.lemitree.web.ui.features.view_tactic.TacticView
import com.lemitree.web.ui.theme.LemiTreeTheme
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules(uiModule)
    }
    val viewModel: ViewModel = getKoinInstance()
    onWasmReady {
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(title = "LemiTree") {
            LemiTreeTheme {
                ComposeWebContent(viewModel)
            }
        }
    }
    // Utilising Compose HTML to use a simple markdown rendering tool.
    renderComposable(rootElementId = "root") {
        LemiTreeTheme {
            ComposeHtmlContent(viewModel)
        }
    }
}

@Composable
fun ComposeWebContent(viewModel: ViewModel) {
    val mdText by viewModel.mdText.collectAsState()
    val tree by viewModel.tree.collectAsState()
    val selectedDirectory by viewModel.selectedDirectory.collectAsState()
    val tactic by viewModel.tactic.collectAsState()
    val isEditing by viewModel.isEditingTactic.collectAsState()
    val contentScrollState = rememberScrollState(0)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(contentScrollState)
    ) {
        TacticTree(
            items = tree,
            selectedPath = selectedDirectory,
            onClickItem = { viewModel.updatePath(it) },
        )
        Column {
            selectedDirectory?.let { path ->
                if (mdText != null) {
                    LemiSwitch(
                        options = SwitchButton(false, "View") to
                                SwitchButton(true, "Edit"),
                        initial = isEditing,
                        onSwitchClicked = { viewModel.updateIsEditing(it) },
                    )
                    if (isEditing) {
                        EditTacticForm(
                            tactic = tactic,
                            selectedPath = path,
                            onClickSubmit = { tactic -> viewModel.editTactic(tactic) },
                        )
                    }
                }
                if (mdText == null) {
                    AddContent(
                        path = path,
                        tactic = tactic,
                        onClickCreateTactic = { newTactic -> viewModel.createTactic(newTactic) },
                        onClickCreateCategory = { newPath -> viewModel.createCategory(newPath) },
                    )
                }
            }
        }
    }
}

@Composable
fun ComposeHtmlContent(viewModel: ViewModel) {
    val mdText by viewModel.mdText.collectAsState()
    val selectedDirectory by viewModel.selectedDirectory.collectAsState()
    val isEditing by viewModel.isEditingTactic.collectAsState()
    selectedDirectory?.let { _ ->
        if (mdText != null && !isEditing) {
            TacticView(mdText!!)
        }
    }
}