package com.lemitree.web.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
    showFieldBorder: Boolean = false,
    onValueChange: (List<T?>) -> Unit,
    fieldContent: @Composable (item: T?, onFieldValueChanged: (T?) -> Unit) -> Unit,
) {
    title?.let {
        LemiFieldTitle(
            text = title,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
    LemiButton(
        text = "+",
        modifier = Modifier.width(40.dp),
        onClick = { onValueChange(fields.plus(null)) },
    )
    fields.forEachIndexed { index, item ->
        val baseFieldModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
        val borderedFieldModifier = baseFieldModifier
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
        val fieldModifier =
            if (showFieldBorder) borderedFieldModifier
            else baseFieldModifier
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = fieldModifier,
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