package com.lemitree.web.ui.features.tree_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.common.data.TreeItem
import com.lemitree.web.ui.components.ContainerInSection
import com.lemitree.web.ui.components.MainContentLayout
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun TacticTree(
    items: List<TreeItem>,
    selectedPath: String?,
    onClickItem: (String) -> Unit,
) {
    MainContentLayout {
        items.forEach {
            TreeItem(
                item = it,
                selectedPath = selectedPath,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
private fun TreeItem(
    item: TreeItem,
    selectedPath: String?,
    onClickItem: (String) -> Unit,
) {
    val textColor = if (selectedPath == item.path) Color.blue else Color.black
    var showChildren by remember { mutableStateOf(false) }
    ContainerInSection {
        Span({
            style { cursor("pointer") }
            onClick {
                showChildren = !showChildren
                onClickItem(item.path)
            }
        }) {
            Div({
                style { color(textColor) }
            }) {
                Text(item.displayName)
            }
        }
        if (showChildren) {
            Div({
                style {
                    marginLeft(16.px)
                }
            }) {
                TacticTree(
                    items = item.children,
                    selectedPath = selectedPath,
                    onClickItem = onClickItem,
                )
            }
        }
    }
}