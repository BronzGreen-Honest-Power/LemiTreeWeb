package com.lemitree.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text

@Composable
fun <T> VariableSizeList(
    title: String? = null,
    fields: List<T?>,
    onValueChanged: (List<T?>) -> Unit,
    fieldContent: @Composable (item: T?, onFieldValueChanged: (T?) -> Unit) -> Unit,
) {
    title?.let {
        Text(title)
    }
    LemiButton(
        text = "+",
        contentStyle = { width(40.px) }
    ) {
        onValueChanged(fields.plus(null))
    }
    fields.forEachIndexed { index, item ->
        Row(
            rowStyle = {
                alignItems(AlignItems.Center)
            }
        ) {
            val btnSize = 20
            LemiButton(
                text = "-",
                contentStyle = {
                    width(btnSize.px)
                    height(btnSize.px)
                    marginLeft(4.px)
                },
            ) {
                onValueChanged(fields.minus(fields[index]))
            }
            fieldContent(item) {
                onValueChanged(fields.toMutableList().apply { set(index, it) })
            }
        }
    }
}