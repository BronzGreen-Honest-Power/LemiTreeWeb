import androidx.compose.runtime.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.w3c.dom.HTMLElement

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

    var count: Int by mutableStateOf(0)

    renderComposable(rootElementId = "root") {
        val viewModel: ViewModel = getKoinInstance()
        val mdText by viewModel.mdText.collectAsState(null, getKoinInstance())
        val scope = rememberCoroutineScope()
        console.log("Quack from Kotlin!")
        Div({ style { padding(25.px) } }) {
            Button(attrs = {
                onClick { count -= 1 }
            }) {
                Text("-")
            }

            Span({ style { padding(15.px) } }) {
                Text("$count")
            }

            Button(attrs = {
                onClick { count += 1 }
            }) {
                Text("+")
            }
        }
        Div({ style { padding(25.px) } }) {
            Idk(getKoinInstance())
        }
        Div({ style { padding(25.px) } }) {
            Button(attrs = {
                onClick {
                    scope.launch {
                        viewModel.fetchContent()
                    }
                }
            }) {
                Text("Quack!")
            }
        }
        mdText?.let { text ->
            Div({ style { padding(25.px) } }) {
                MarkDown(text)
            }
        }
    }
}

/**
    md-block puts a "rendered" attribute into the <md-block> tag once markdown is rendered.
    This is useful as it allows styling, e.g. to hide the view before it's been fully rendered.
    However, this also prevents updating the view on recomposition.
    So to recompose the markdown view, you must first nullify text state value to remove the
    <md-block> tag from html before creating a new md-block with a new value in it.
 */
@Composable
fun MarkDown(
    mdText: String,
    attrs: AttrBuilderContext<HTMLElement>? = null,
) {
    HtmlTag(
        tagName = "md-block",
        attrs = attrs,
    ) {
        Text(mdText)
    }
}

@Composable
fun HtmlTag(
    tagName: String,
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: ContentBuilder<HTMLElement>? = null,
) {
    TagElement(
        elementBuilder = {
            document.createElement(tagName).cloneNode() as HTMLElement
        },
        applyAttrs = attrs,
        content = content,
    )
}

@Composable
fun Idk(x: Sesamez) {
    Text(x.open)
}