package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.Section

@Composable
fun Layout(content: @Composable () -> Unit) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.percent)
            margin(0.px)
            boxSizing("border-box")
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