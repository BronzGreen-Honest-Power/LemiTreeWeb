package com.lemitree.web.ui.features.view_tactic

import androidx.compose.runtime.Composable
import com.lemitree.web.ui.components.HtmlTag
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

/**
md-block puts a "rendered" attribute into the <md-block> tag once markdown is rendered.
This is useful as it allows styling, e.g. to hide the view before it's been fully rendered.
However, this also prevents updating the view on recomposition.
So to recompose the markdown view, you must first nullify text state value to remove the
<md-block> tag from html before creating a new md-block with a new value in it.
 */
@Composable
fun MarkDown(
    mdText: String,
    attrs: AttrBuilderContext<HTMLElement>? = null,
) {
    HtmlTag(
        tagName = "md-block",
        attrs = attrs,
    ) {
        Text(mdText)
    }
}