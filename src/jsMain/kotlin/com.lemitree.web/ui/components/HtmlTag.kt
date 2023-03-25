package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLElement

@Composable
fun HtmlTag(
    tagName: String,
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: ContentBuilder<HTMLElement>? = null,
) {
    TagElement(
        elementBuilder = {
            document.createElement(tagName).cloneNode() as HTMLElement
        },
        applyAttrs = attrs,
        content = content,
    )
}