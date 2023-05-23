package com.lemitree.web.ui.features.edit_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.web.ui.components.Column
import com.lemitree.web.ui.components.LemiButton
import com.lemitree.web.ui.components.LemiTextField
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text

@Composable
fun EditCategoryForm(
    selectedPath: String,
    onClickSubmit: (String) -> Unit,
) {
    var categoryName by remember { mutableStateOf("") }
    val newPath = "${selectedPath}/${categoryName}"
    Column {
        Text("Path:")
        LemiTextField(
            text = newPath,
            readonly = true,
        ) {
            textFieldStyle()
            cursor("pointer")
        }

        Text("Category name:")
        LemiTextField(
            text = categoryName,
            onValueChanged = { categoryName = it }
        ) {
            textFieldStyle()
            cursor("pointer")
        }

        LemiButton(
            text = "Create category",
            contentStyle = {
                width(columnWidth.px)
                marginTop(10.px)
                marginBottom(10.px)
            }
        ) { //todo invalid required fields to throw error
            onClickSubmit(newPath)
        }
    }
}