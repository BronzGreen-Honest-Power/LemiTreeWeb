package com.lemitree.web.ui.features.tree_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.TreeItem

@Composable
fun TacticTree(
    items: List<TreeItem>,
    selectedPath: String?,
    onClickItem: (String) -> Unit,
) {
    val padding = if (items.isEmpty()) 0 else 10
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(300.dp)
            .padding(padding.dp)
    ) {
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
    val textColor = if (selectedPath == item.path) Color.Blue else Color.Black
    var showChildren by remember { mutableStateOf(false) }
    Column {
        Text(
            text = item.displayName,
            color = textColor,
            modifier = Modifier
                .clickable {
                    showChildren = !showChildren
                    onClickItem(item.path)
                }
                .pointerHoverIcon(PointerIcon.Hand)
        )
        if (showChildren) {
            TacticTree(
                items = item.children,
                selectedPath = selectedPath,
                onClickItem = onClickItem,
            )
        }
    }
}