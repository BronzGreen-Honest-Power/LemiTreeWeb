package com.lemitree.web.ui.features.edit_content

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.LemiSwitch
import com.lemitree.web.ui.components.SwitchButton

enum class ElementType { TACTIC, CATEGORY }

@Composable
fun EditContent(
    path: String,
    onClickEditTactic: (Tactic) -> Unit,
    onClickCreateCategory: (String) -> Unit,
) {
    var selected by remember { mutableStateOf(ElementType.TACTIC) }
    LemiSwitch(
        options = SwitchButton(ElementType.TACTIC, "New tactic") to
                SwitchButton(ElementType.CATEGORY, "New category"),
        initial = selected,
        modifier = Modifier, //todo
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