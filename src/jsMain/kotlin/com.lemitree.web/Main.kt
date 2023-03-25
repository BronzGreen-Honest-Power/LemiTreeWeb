package com.lemitree.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.lemitree.web.ui.components.MarkDown
import com.lemitree.common.helpers.getKoinInstance
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
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
        Div({ style { padding(25.px) } }) {
            Idk(getKoinInstance())
            var text by remember { mutableStateOf("Initial value") }
            Input(type = InputType.Text) {
                value(text)
                onInput { event -> text = event.value }
            }
        }
        FetchButton {
            scope.launch {
//                viewModel.fetchContent()
            }
        }
        Tactic(mdText)
        Tree(
            list = tree,
            onClickItem = {
                viewModel.updatePath(it)
            }
        )
    }
}

@Composable
private fun Tree(
    list: List<String>,
    onClickItem: (String) -> Unit,
) {

}

@Composable
private fun FetchButton(
    onClick: () -> Unit,
) {
    Div({ style { padding(25.px) } }) {
        Button(attrs = {
            onClick {
                onClick()
            }
        }) {
            Text("Quack!")
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