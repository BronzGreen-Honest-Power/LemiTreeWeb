package com.lemitree.web.ui.features.view_tactic

import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.web.dom.Div

@Composable
fun TacticView(
    mdText: String,
) {
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
        MarkDown(mdText) {
            style {
                width(800.px)
                fontFamily("Arial")
            }
        }
    }
}