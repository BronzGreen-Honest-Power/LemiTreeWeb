package com.lemitree.web.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun SimpleDropdownMenu(
    modifier: Modifier = Modifier,
    hint: String = "",
    selected: String? = null,
    items: List<String>,
    offset: DpOffset = DpOffset(0.dp, 0.dp), //todo?
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        val textStyle = if (selected != null) LocalTextStyle.current.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary,
            fontStyle = FontStyle.Normal,
        ) else LocalTextStyle.current
        OutlinedTextField(
            value = selected ?: hint,
            onValueChange = {},
            readOnly = true,
            modifier = modifier.clickable {
                expanded = !expanded
                selected ?: return@clickable
                onItemSelected(selected)
            },
            enabled = false,
            textStyle = textStyle,
            trailingIcon = {
                IconButton(
                    onClick = { onItemSelected("") },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear value",
                    )
                }
            }
        )
        if (expanded) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .zIndex(2f)
                    .padding(vertical = 2.dp),
                elevation = 4.dp,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    items.forEach {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .requiredWidth(200.dp)
                                .padding(4.dp)
                                .clickable {
                                    expanded = !expanded
                                    onItemSelected(it)
                                }
                        ) {
                            Text(it)
                        }
                    }
                }
            }
        }
    }
}