package com.lemitree.web.ui.features.view_tactic

import androidx.compose.runtime.Composable
import com.lemitree.web.ui.theme.LocalWindowSize
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.overflowY
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div

@Composable
fun TacticView(
    mdText: String,
) {
    val marginLeft = LocalWindowSize.current.leftColWidth
    val documentPadding = 25
    val documentWidth = LocalWindowSize.current.centerColWidth - (documentPadding * 2)
    val topMenuHeight = LocalWindowSize.current.topMenuHeight
    val documentHeight = LocalWindowSize.current.height - topMenuHeight - documentPadding
    Div(
        attrs = {
            style {
                height(documentHeight.px)
                width(documentWidth.px)
                paddingLeft(documentPadding.px)
                paddingRight(documentPadding.px)
                paddingBottom(documentPadding.px)
                position(Position.Absolute)
                marginTop(topMenuHeight.px)
                marginLeft(marginLeft.px)
                overflowY("scroll")
            }
        }
    ) {
        MarkDown(mdText) {
            style {
                fontFamily("Arial")
            }
        }
    }
}