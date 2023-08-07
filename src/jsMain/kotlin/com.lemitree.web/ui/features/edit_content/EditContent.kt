package com.lemitree.web.ui.features.edit_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.Switch
import com.lemitree.web.ui.components.SwitchButton
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px

enum class ElementType { TACTIC, CATEGORY }

@Composable
fun EditContent(
    path: String,
    onClickEditTactic: (Tactic) -> Unit,
    onClickCreateCategory: (String) -> Unit,
) {
    var selected by remember { mutableStateOf(ElementType.TACTIC) }
    Switch(
        options = SwitchButton(ElementType.TACTIC, "New tactic") to
                SwitchButton(ElementType.CATEGORY, "New category"),
        initial = selected,
        contentStyle = {
            paddingTop(10.px)
            paddingBottom(10.px)
        },
        onSwitchClicked = { selected = it },
    )
    when (selected) {
        ElementType.TACTIC -> {
            EditTacticForm(
                selectedPath = path,
                onClickSubmit = onClickEditTactic,
            )
        }
        ElementType.CATEGORY -> {
            EditCategoryForm(
                selectedPath = path,
                onClickSubmit = onClickCreateCategory,
            )
        }
    }
}