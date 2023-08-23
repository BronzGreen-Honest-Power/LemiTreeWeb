package com.lemitree.web.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

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
        OutlinedTextField(
            value = selected ?: hint,
            onValueChange = {},
            readOnly = true,
            modifier = modifier.clickable {
                expanded = !expanded
                selected ?: return@clickable
                onItemSelected(selected)
            }
        )
        if (expanded) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 2.dp),
                elevation = 4.dp,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    items.forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
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