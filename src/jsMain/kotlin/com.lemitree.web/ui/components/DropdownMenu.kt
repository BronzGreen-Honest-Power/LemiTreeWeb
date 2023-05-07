package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.order
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.transform
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun DropdownMenu(
    items: List<String>,
    selected: String?,
    onItemSelected: (String) -> Unit,
    contentStyle: StyleScope.() -> Unit = {},
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Div({
        style {
            position(Position.Relative)
            contentStyle()
        }
    }) {
        // Main button to show selected item and expand/collapse dropdown
        Div({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.SpaceBetween)
                border(1.px, LineStyle.Solid, Color.gray)
                borderRadius(4.px)
                padding(4.px)
                cursor("pointer")
            }
            onClick {
                dropdownExpanded = !dropdownExpanded
            }
        }) {
            Text(if (dropdownExpanded) "▲" else "▼")
            Text(selected ?: "")
        }
        // Dropdown menu with list of items
        if (dropdownExpanded) {
            Div({
                style {
                    position(Position.Absolute)
                    top(100.percent)
                    left(0.px)
                    right(0.px)
                    backgroundColor(Color.white)
                    border(1.px, LineStyle.Solid, Color.gray)
                    borderRadius(4.px)
                    transform {
                        translateZ(4.px)
                    }
                }
            }) {
                items.forEach { item ->
                    Div({
                        style {
                            padding(4.px)
                            if (item == selected) {
                                backgroundColor(Color.gray)
                                color(Color.white)
                            }
                            cursor("pointer")
                        }
                        onClick {
                            onItemSelected(item)
                            dropdownExpanded = false
                        }
                    }) {
                        Text(item)
                    }
                }
            }
        }
    }
}
