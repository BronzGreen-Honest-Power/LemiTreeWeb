import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.overflowY
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

// todo make reusable

@Composable
fun ScrollableItemList(items: List<String>) {
    var visibleItems by remember { mutableStateOf(items.take(10)) }
    var lastVisibleIndex by remember { mutableStateOf(9) }

    Div({
        style {
            overflowY("auto")
            height(100.px)
            padding(16.px)
        }
        onScroll {
            val target = it.target as? HTMLElement ?: return@onScroll
            val scrollTop = target.scrollTop
            val scrollHeight = target.scrollHeight
            val clientHeight = target.clientHeight
            val scrollBottom = scrollHeight - scrollTop - clientHeight
            if (scrollBottom < 50 && lastVisibleIndex < items.lastIndex) {
                val newIndex = lastVisibleIndex + 1
                visibleItems.toMutableList().apply {
                    add(items[newIndex])
                }.also {
                    lastVisibleIndex = newIndex
                    visibleItems = it
                }
            }
        }
    }) {
        visibleItems.forEach { item ->
            Div({
                style {
                    padding(8.px)
                    backgroundColor(if (visibleItems.indexOf(item) % 2 == 0) Color.darkblue else Color.white)
                }
            }) {
                Text(item)
            }
        }

        if (lastVisibleIndex < items.lastIndex) {
            Div({
                style {
                    textAlign("center")
                    padding(8.px)
                    backgroundColor(Color.aliceblue)
                    color(Color.white)
                    cursor("pointer")
                }
                onClick {
                    val newIndex = lastVisibleIndex + 1
                    visibleItems.toMutableList().apply {
                        add(items[newIndex])
                    }.also {
                        lastVisibleIndex = newIndex
                        visibleItems = it
                    }
                }
            }) {
                Text("Loading more items...")
            }
        } else {
            Div({
                style {
                    textAlign("center")
                    padding(8.px)
                    backgroundColor(Color.aliceblue)
                    color(Color.white)
                }
            }) {
                Text("No more items to load.")
            }
        }
    }
}