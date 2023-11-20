package com.lemitree.web.ui.features.view_tactic

import androidx.compose.runtime.Composable
import com.lemitree.web.ui.theme.LocalWindowSize
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.overflow
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div

@Composable
fun TacticView(
    mdText: String,
) {
    val marginLeft = LocalWindowSize.current.leftColWidth.px
    val documentWidth = LocalWindowSize.current.centerColWidth.px
    Div(
        attrs = {
            style {
                display(DisplayStyle.Flex)
                border(5.px, LineStyle.Solid, Color.cadetblue)
                padding(25.px)
                height(800.px)
                backgroundColor(Color.aliceblue)
                position(Position.Absolute)
                marginTop(50.px)
                marginLeft(marginLeft)
                overflow("scroll")
            }
        }
    ) {
        MarkDown(mdText) {
            style {
                width(documentWidth)
                fontFamily("Arial")
            }
        }
    }
}