package com.lemitree.web.ui.features.contributor_section

import androidx.compose.runtime.Composable
import com.lemitree.web.ui.theme.LocalWindowSize
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.overflowY
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Iframe
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun ContributorSection(
//todo
) {
    val verticalPadding = 16
    val horizontalPadding = 32
    val videoWidth = LocalWindowSize.current.rightColWidth - (horizontalPadding * 2)
    val videoHeight = 200
    val marginLeft = (LocalWindowSize.current.leftColWidth + LocalWindowSize.current.centerColWidth)
    Div({
        style {
            position(Position.Absolute)
            paddingTop(verticalPadding.px)
            paddingBottom(verticalPadding.px)
            paddingRight(horizontalPadding.px)
            paddingLeft(horizontalPadding.px)
            marginLeft(marginLeft.px)
            overflowY("scroll")
        }
    }) {
        H2({
            style {
                paddingTop(verticalPadding.px)
                paddingBottom((verticalPadding * 2).px)
                textAlign("center")
                fontFamily("Arial")
            }
        }) {
            Text("Contribute tutorial")
        }
        Iframe({
            style {
                borderRadius(6.px)
                width(videoWidth.px)
                height(videoHeight.px)
            }
            attr("src", "https://www.youtube.com/embed/sC7u_D50XLw")
        })
        P({
            style {
                paddingTop(verticalPadding.px)
                fontFamily("Arial")
            }
        }) {
           Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus hendrerit libero vel eleifend ultricies. Maecenas nibh leo, porta et efficitur nec, vehicula nec nisl. Sed tempus metus ut turpis hendrerit, id porta sapien rhoncus. Aliquam quis neque id magna feugiat interdum eget nec augue. Phasellus rutrum nisi non libero convallis, eu sodales ligula ornare. Vivamus bibendum convallis ante, id blandit libero pulvinar ac. Nulla facilisi. Curabitur egestas rhoncus interdum. Donec vehicula nulla id aliquam pretium. In ac leo id arcu dignissim dictum sed accumsan libero. Nam nec hendrerit tortor.")
        }
        P({
            style {
                fontFamily("Arial")
            }
        }) {
            Text("In hac habitasse platea dictumst. In quis mi elit. Morbi convallis magna magna, eget viverra eros consequat vitae. Fusce vitae condimentum urna. Quisque quis ipsum quam. Quisque et ante tellus. Vestibulum quis ante in quam imperdiet pulvinar. Aenean vitae maximus lacus. Nunc id tempor orci.")
        }
    }
}