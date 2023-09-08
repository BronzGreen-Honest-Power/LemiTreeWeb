package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditCategoryForm(
    selectedPath: String,
    onClickSubmit: (String) -> Unit,
) {
    var categoryName by remember { mutableStateOf("") }
    val newPath = "${selectedPath}/${categoryName}"
    Column {
        Text("Path:")
        OutlinedTextField(
            value = newPath,
            onValueChange = { },
            readOnly = true,
        )

        Text("Category name:")
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it }
        )

        Button(
            onClick = { onClickSubmit(newPath) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        ) { //todo invalid required fields to throw error
            Text("Create category")
        }
    }
}