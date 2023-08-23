package com.lemitree.web.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> VariableSizeList(
    title: String? = null,
    fields: List<T?>,
    onValueChange: (List<T?>) -> Unit,
    fieldContent: @Composable (item: T?, onFieldValueChanged: (T?) -> Unit) -> Unit,
) {
    title?.let {
        Text(title)
    }
    LemiButton(
        text = "+",
        modifier = Modifier.width(40.dp),
        onClicked = { onValueChange(fields.plus(null)) },
    )
    fields.forEachIndexed { index, item ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val btnSize = 20
            LemiButton(
                text = "-",
                modifier = Modifier.size(btnSize.dp)
                    .padding(start = 4.dp),
                onClicked =  { onValueChange(fields.minus(fields[index])) },
            )
            fieldContent(item) {
                onValueChange(fields.toMutableList().apply { set(index, it) })
            }
        }
    }
}