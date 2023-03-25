import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.browser.document
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
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.TagElement
import org.jetbrains.compose.web.dom.Text
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