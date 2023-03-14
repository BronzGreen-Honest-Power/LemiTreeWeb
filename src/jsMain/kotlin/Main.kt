import androidx.compose.runtime.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
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
                Text(text)
            }
        }
    }
}

@Composable
fun Idk(x: Sesamez) {
    Text(x.open)
}