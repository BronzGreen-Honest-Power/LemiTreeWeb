package com.lemitree.web.ui.features.tree_view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.TreeItem
import com.lemitree.web.ui.theme.LocalWindowSize

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
            .width(LocalWindowSize.current.leftColWidthDp)//todo
            .padding(padding.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        items.forEach {
            TreeItem(
                item = it,
                selectedPath = selectedPath,
                onClickItem = onClickItem,
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
    val textColor = MaterialTheme.colors.onPrimary//if (selectedPath == item.path) Color.Blue else Color.Black //todo
    var showChildren by remember { mutableStateOf(false) }
    Column {
        OutlinedButton(
            onClick = {
                showChildren = !showChildren
                onClickItem(item.path)
            },
            border = BorderStroke(1.dp, textColor),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor, backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
        ) {
            Text(text = item.displayName)
        }
        if (showChildren) {
            TacticTree(
                items = item.children,
                selectedPath = selectedPath,
                onClickItem = onClickItem,
            )
        }
    }
}