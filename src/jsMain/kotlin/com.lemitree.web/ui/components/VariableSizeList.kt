package com.lemitree.web.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
        onClick = { onValueChange(fields.plus(null)) },
    )
    fields.forEachIndexed { index, item ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(
                onClick = { onValueChange(fields.minus(fields[index])) },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete benefit",
                    tint = MaterialTheme.colors.primary,
                )
            }
            fieldContent(item) {
                onValueChange(fields.toMutableList().apply { set(index, it) })
            }
        }
    }
}