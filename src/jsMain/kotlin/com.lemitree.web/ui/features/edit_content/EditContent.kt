package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.lemitree.common.data.Tactic
import com.lemitree.web.ui.components.LemiSwitch
import com.lemitree.web.ui.components.SwitchButton
import com.lemitree.web.ui.theme.LocalWindowSize

enum class ElementType { TACTIC, CATEGORY }

@Composable
fun AddContent(
    path: String,
    tactic: Tactic = Tactic.EMPTY,
    onClickCreateTactic: (Tactic) -> Unit,
    onClickCreateCategory: (String) -> Unit,
) {
    var selected by remember { mutableStateOf(ElementType.TACTIC) }
    LemiSwitch(
        options = SwitchButton(ElementType.TACTIC, "New tactic") to
                SwitchButton(ElementType.CATEGORY, "New category"),
        initial = selected,
        modifier = Modifier.height(LocalWindowSize.current.topMenuHeightDp),
        onSwitchClicked = { selected = it },
    )
    when (selected) {
        ElementType.TACTIC -> {
            EditTacticForm(
                tactic = tactic,
                selectedPath = path,
                onClickSubmit = onClickCreateTactic,
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