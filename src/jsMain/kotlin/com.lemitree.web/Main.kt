package com.lemitree.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.lemitree.common.data.TreeItem
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.web.ui.components.ContainerInSection
import com.lemitree.web.ui.components.Layout
import com.lemitree.web.ui.components.MainContentLayout
import com.lemitree.web.ui.components.MarkDown
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

external fun examplefunc() // Don't call directly in composables, must match function name in js file

val uiModule = module {
    single { Sesamez("quackz") }
    single<CoroutineContext> { EmptyCoroutineContext }
    single { CoroutineScope(get()) }
    single { ViewModel(get()) }
}
data class Sesamez(val open: String)

fun main() {
    startKoin {
        modules(uiModule)
    }

    renderComposable(rootElementId = "root") {
        console.log("Main started")
        val scope = rememberCoroutineScope()
        val viewModel: ViewModel = getKoinInstance()
        val mdText by viewModel.mdText.collectAsState()
        val tree by viewModel.tree.collectAsState()
        val selectedPath by viewModel.selectedPath.collectAsState()
        Layout {
            Div({ style { padding(25.px) } }) {
                Idk(getKoinInstance())
                var text by remember { mutableStateOf("Initial value") }
                Input(type = InputType.Text) {
                    value(text)
                    onInput { event -> text = event.value }
                }
            }
            Tactic(mdText)
            Tree(
                items = tree,
                selectedPath = selectedPath,
                onClickItem = {
                    viewModel.updatePath(it)
                }
            )
        }
    }
}

@Composable
private fun Tree(
    items: List<TreeItem>,
    selectedPath: String?,
    onClickItem: (String) -> Unit,
) {
    MainContentLayout {
        items.forEach {
            TreeItem(
                item = it,
                selectedPath = selectedPath,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
private fun TreeItem(
    item: TreeItem,
    selectedPath: String?,
    onClickItem: (String) -> Unit,
) {
    val textColor = if (selectedPath == item.path) Color.blue else Color.black
    var showChildren by remember { mutableStateOf(false) }
    ContainerInSection {
        Span({
            onClick {
                showChildren = !showChildren
                onClickItem(item.path)
            }
        }) {
            Div({
                style { color(textColor) }
            }) {
                Text(item.displayName)
            }
        }
        if (showChildren) {
            Div({
                style {
                    marginLeft(16.px)
                }
            }) {
                Tree(
                    items = item.children,
                    selectedPath = selectedPath,
                    onClickItem = onClickItem,
                )
            }
        }
    }
}

@Composable
private fun Tactic(
    mdText: String?,
) {
    mdText?.let { text ->
        Div(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    border(5.px, LineStyle.Solid, Color.cadetblue)
                    padding(25.px)
                    backgroundColor(Color.aliceblue)
                }
            }
        ) {
            MarkDown(text) {
                style {
//                    property("font-family", "Arial")
                    width(800.px)
                    fontFamily("Arial")
                }
            }
        }
    }
}

@Composable
fun Idk(x: Sesamez) {
    Text(x.open)
}