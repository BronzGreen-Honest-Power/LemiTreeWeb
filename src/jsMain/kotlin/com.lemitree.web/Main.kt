package com.lemitree.web

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.web.ui.features.edit_content.EditContent
import com.lemitree.web.ui.features.tree_view.TacticTree
import com.lemitree.web.ui.features.view_tactic.TacticView
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.skiko.ClipboardManager

val uiModule = module {
    single<CoroutineContext> { EmptyCoroutineContext }
    single { CoroutineScope(get()) }
    single { ViewModel(get()) }
    single { ClipboardManager() }
}

fun main() {
    startKoin {
        modules(uiModule)
    }
    onWasmReady {
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(title = "LemiTree") {
            val viewModel: ViewModel = getKoinInstance()
            val mdText by viewModel.mdText.collectAsState()
            val tree by viewModel.tree.collectAsState()
            val selectedPath by viewModel.selectedPath.collectAsState()
            val contentScrollState = rememberScrollState(0)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                TacticTree(
                    items = tree,
                    selectedPath = selectedPath,
                    onClickItem = { viewModel.updatePath(it) },
                )
                Column(
                    modifier = Modifier.verticalScroll(contentScrollState)
                ) {
                    selectedPath?.let { path ->
                        if (mdText == null) {
                            EditContent(
                                path = path,
                                onClickEditTactic = { newTactic -> viewModel.editTactic(newTactic) },
                                onClickCreateCategory = { newPath -> viewModel.createCategory(newPath) },
                            )
                        }
                    }
                }
            }
        }
    }
    // Utilising Compose HTML to use a simple markdown rendering tool.
    renderComposable(rootElementId = "root") {
        val viewModel: ViewModel = getKoinInstance()
        val mdText by viewModel.mdText.collectAsState()
        val selectedPath by viewModel.selectedPath.collectAsState()
        selectedPath?.let { path ->
            if (mdText != null) {
                TacticView(mdText!!)
            }
        }
    }
}