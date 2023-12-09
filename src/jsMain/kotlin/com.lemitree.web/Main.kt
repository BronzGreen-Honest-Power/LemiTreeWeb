package com.lemitree.web

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.web.ui.components.LemiSwitch
import com.lemitree.web.ui.components.SwitchButton
import com.lemitree.web.ui.features.contributor_section.ContributorSection
import com.lemitree.web.ui.features.edit_content.AddContent
import com.lemitree.web.ui.features.edit_content.EditTacticForm
import com.lemitree.web.ui.features.tree_view.TacticTree
import com.lemitree.web.ui.features.view_tactic.TacticView
import com.lemitree.web.ui.theme.LemiTreeTheme
import com.lemitree.web.ui.theme.LocalWindowSize
import com.lemitree.web.ui.theme.WindowSize
import kotlinx.browser.window
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules(uiModule)
    }
    val viewModel: ViewModel = getKoinInstance()
    val windowSize = WindowSize(height = window.innerHeight, width = window.innerWidth)
    onWasmReady {
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(title = "LemiTree") {
            LemiTreeTheme {
                CompositionLocalProvider(LocalWindowSize provides windowSize) {
                    ComposeWebContent(viewModel)
                }
            }
        }
    }
    // Utilising Compose HTML to use a simple markdown rendering tool.
    renderComposable(rootElementId = "root") {
        LemiTreeTheme {
            CompositionLocalProvider(LocalWindowSize provides windowSize) {
                ComposeHtmlContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComposeWebContent(viewModel: ViewModel) {
    val mdText by viewModel.mdText.collectAsState()
    val tree by viewModel.tree.collectAsState()
    val selectedDirectory by viewModel.selectedDirectory.collectAsState()
    val tactic by viewModel.tactic.collectAsState()
    val isEditing by viewModel.isEditingTactic.collectAsState()
    val contentScrollState = rememberScrollState(0)
    val treeScrollState = rememberScrollState(0)
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(LocalWindowSize.current.height.dp)
                .background(color = MaterialTheme.colors.primary)
                .verticalScroll(treeScrollState)
        ) {
            Image(
                painter = painterResource("lemitree-logo.jpeg"),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(18.dp),
            )
            TacticTree(
                items = tree,
                selectedPath = selectedDirectory,
                onClickItem = { viewModel.updatePath(it) },
            )
        }
        Column(
            modifier = Modifier
                .width(LocalWindowSize.current.centerColWidthDp)
                .background(color = MaterialTheme.colors.background)
                .padding(18.dp)
                .verticalScroll(contentScrollState)
        ) {
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
    ContributorSection()
}