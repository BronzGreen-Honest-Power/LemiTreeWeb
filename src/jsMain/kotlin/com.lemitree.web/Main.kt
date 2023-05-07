package com.lemitree.web

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.web.ui.components.Column
import com.lemitree.web.ui.components.SideMenu
import com.lemitree.web.ui.features.edit_tactic.EditTacticForm
import com.lemitree.web.ui.features.tree_view.TacticTree
import com.lemitree.web.ui.features.view_tactic.TacticView
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val uiModule = module {
    single<CoroutineContext> { EmptyCoroutineContext }
    single { CoroutineScope(get()) }
    single { ViewModel(get()) }
}

fun main() {
    startKoin {
        modules(uiModule)
    }
    renderComposable(rootElementId = "root") {
        val viewModel: ViewModel = getKoinInstance()
        val mdText by viewModel.mdText.collectAsState()
        val tree by viewModel.tree.collectAsState()
        val selectedPath by viewModel.selectedPath.collectAsState()
        SideMenu {
            TacticTree(
                items = tree,
                selectedPath = selectedPath,
                onClickItem = {
                    viewModel.updatePath(it)
                }
            )
        }
        Column(
            columnStyle = {
                position(Position.Absolute)
                left(325.px)
            }
        ) {
            selectedPath?.let { path ->
                if (mdText == null) {
                    EditTacticForm(
                        selectedPath = path,
                        onClickSubmit = { newTactic ->  viewModel.create(newTactic) }
                    )
                } else {
                    TacticView(mdText!!)
                }
            }
        }
    }
}