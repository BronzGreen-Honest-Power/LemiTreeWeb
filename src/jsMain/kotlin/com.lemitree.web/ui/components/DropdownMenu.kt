package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Text

@Composable
fun DropdownMenu(
    items: List<String>,
    selected: String?, // todo needed for editing tactics
    onItemSelected: (String) -> Unit,
    contentStyle: StyleScope.() -> Unit = {},
    itemStyle: StyleScope.() -> Unit = {},
) {
    Select({
        style {
            backgroundColor(Color.white)
            display(DisplayStyle.Flex)
            position(Position.Relative)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.SpaceBetween)
            padding(4.px)
            cursor("pointer")
            contentStyle()
        }
        onInput { onItemSelected(it.value ?: "") }
    }) {
        items.forEach { item ->
            Option(
                value = item,
                attrs = {
                    style {
                        cursor("pointer")
                        itemStyle()
                    }
                },
            ) {
                Text(item)
            }
        }
    }
}
