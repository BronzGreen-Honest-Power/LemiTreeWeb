package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.Section
import org.w3c.dom.HTMLDivElement

@Composable
fun Column(
    columnStyle: StyleScope.() -> Unit = {},
    content: @Composable ElementScope<HTMLDivElement>.() -> Unit
) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            flexWrap(FlexWrap.Wrap)
            boxSizing("border-box")
            columnStyle()
        }
    }) {
        content()
    }
}

@Composable
fun Row(
    rowStyle: StyleScope.() -> Unit = {},
    content: @Composable ElementScope<HTMLDivElement>.() -> Unit
) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            flexWrap(FlexWrap.Wrap)
            boxSizing("border-box")
            rowStyle()
        }
    }) {
        content()
    }
}

@Composable
fun MainContentLayout(content: @Composable () -> Unit) {
    Main({
        style {
            flex("1 0 auto")
            boxSizing("border-box")
        }
    }) {
        content()
    }
}

@Composable
fun ContainerInSection(content: @Composable () -> Unit) {
    Section {
        Div {
            content()
        }
    }
}