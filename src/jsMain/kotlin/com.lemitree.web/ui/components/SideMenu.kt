package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLDivElement

@Composable
fun SideMenu(
    content: @Composable ElementScope<HTMLDivElement>.() -> Unit,
) {
    Div({
        style {
            width(250.px)
            height(100.percent)
            backgroundColor(Color.aliceblue)
            position(Position.Fixed)
            top(0.percent)
            left(0.percent)
            padding(20.px, 30.px)
        }
    }) {
        content()
    }
}