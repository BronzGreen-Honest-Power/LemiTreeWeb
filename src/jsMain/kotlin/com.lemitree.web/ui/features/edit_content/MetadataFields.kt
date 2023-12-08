package com.lemitree.web.ui.features.edit_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lemitree.common.data.Energy
import com.lemitree.common.data.EnergyCost
import com.lemitree.common.data.Expenses
import com.lemitree.common.data.Frequency
import com.lemitree.common.data.FrequencyType
import com.lemitree.common.data.TacticMetadata
import com.lemitree.common.data.TimeDuration
import com.lemitree.web.ui.components.NumberOutlinedTextField
import com.lemitree.web.ui.components.SimpleDropdownMenu
import com.lemitree.web.ui.components.LemiFieldTitle
import com.lemitree.web.ui.theme.LocalWindowSize

@Composable
fun MetadataFields(
    data: TacticMetadata?,
    onMetadataChanged: (TacticMetadata?) -> Unit,
) {
    val fieldWidth = LocalWindowSize.current.centerColWidthDp
    var frequency by remember { mutableStateOf(data?.frequency) }
    var timeDuration by remember { mutableStateOf(data?.timeDuration) }
    var expenses by remember { mutableStateOf(data?.expenses) }
    var energy by remember { mutableStateOf(data?.energy) }
    remember(frequency, timeDuration, expenses, energy) {
        val newMetadata = (data ?: TacticMetadata.EMPTY).copy(
            frequency = frequency,
            timeDuration = timeDuration,
            expenses = expenses,
            energy = energy,
        )
        onMetadataChanged(newMetadata)
    }
    FrequencyFields(
        fieldWidth = fieldWidth / 2,
        frequency = frequency,
        onValueChanged = { frequency = it },
    )
    TimeDurationFields(
        fieldWidth = fieldWidth / 2,
        timeDuration = timeDuration,
        onValueChanged = { timeDuration = it },
    )
    ExpensesFields(
        fieldWidth = fieldWidth / 2,
        expenses = expenses,
        onValueChanged = { expenses = it },
    )
    EnergyFields(
        fieldWidth = fieldWidth / 2,
        energy = energy,
        onValueChanged = { energy = it },
    )
}

@Composable
private fun FrequencyFields(
    fieldWidth: Dp,
    frequency: Frequency?,
    onValueChanged: (Frequency?) -> Unit,
) {
    var frequencyType by remember { mutableStateOf(frequency?.type) }
    var frequencyInterval by remember { mutableStateOf(frequency?.interval) }
    remember(frequencyInterval, frequencyType) {
        val newFrequency =
            if (frequencyType != null && frequencyInterval != null)
                Frequency(type = frequencyType!!, interval = frequencyInterval!!)
            else null
        onValueChanged(newFrequency)
    }
    LemiFieldTitle(
        text = "Frequency:",
        modifier = Modifier.padding(vertical = 8.dp),
    )
    Row {
        SimpleDropdownMenu(
            items = FrequencyType.displayNames(),
            selected = frequencyType?.name,
            onItemSelected = { frequencyType = FrequencyType.fromString(it) },
            modifier = Modifier.defaultMinSize(minWidth = fieldWidth)
        )
        Spacer(modifier = Modifier.width(4.dp))
        NumberOutlinedTextField(
            value = frequencyInterval?.toString() ?: "",
            onValueChange = { frequencyInterval = it },
            hint = "Interval",
            modifier = Modifier.width(fieldWidth),
        )
    }
}

@Composable
private fun TimeDurationFields(
    fieldWidth: Dp,
    timeDuration: TimeDuration?,
    onValueChanged: (TimeDuration?) -> Unit,
) {
    var durationHours by remember { mutableStateOf(timeDuration?.hours) }
    var durationMinutes by remember { mutableStateOf(timeDuration?.minutes) }
    remember(durationHours, durationMinutes) {
        val newTimeDuration =
            if (durationHours != null && durationMinutes != null)
                TimeDuration(hours = durationHours!!, minutes = durationMinutes!!)
            else null
        onValueChanged(newTimeDuration)
    }
    LemiFieldTitle(
        text = "Time duration:",
        modifier = Modifier.padding(vertical = 8.dp),
    )
    Row {
        NumberOutlinedTextField(
            value = durationHours?.toString() ?: "",
            onValueChange = { durationHours = it },
            hint = "Hours",
            modifier = Modifier.width(fieldWidth),
        )
        Spacer(modifier = Modifier.width(4.dp))
        NumberOutlinedTextField(
            value = durationMinutes?.toString() ?: "",
            onValueChange = { durationMinutes = if (it != null && it > 59) 59 else it },
            hint = "Minutes",
            modifier = Modifier.width(fieldWidth),
        )
    }
}

@Composable
private fun ExpensesFields(
    fieldWidth: Dp,
    expenses: Expenses?,
    onValueChanged: (Expenses?) -> Unit,
) {
    var expensesAmount by remember { mutableStateOf(expenses?.cost) }
    var expensesRepeating by remember { mutableStateOf(false) }
    var expensesFrequencyType by remember { mutableStateOf(expenses?.frequency?.type) }
    var expensesFrequencyInterval by remember { mutableStateOf(expenses?.frequency?.interval) }
    val expensesFrequency = remember(expensesFrequencyType, expensesFrequencyInterval) {
        if (expensesFrequencyType != null && expensesFrequencyInterval != null)
            Frequency(type = expensesFrequencyType!!, interval = expensesFrequencyInterval!!)
        else null
    }
    remember(expensesAmount, expensesRepeating, expensesFrequency) {
        val newExpenses = expensesAmount?.let {
            Expenses(cost = it, frequency = expensesFrequency)
        }
        onValueChanged(newExpenses)
    }
    LemiFieldTitle(
        text = "Expenses:",
        modifier = Modifier.padding(vertical = 8.dp),
    )
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberOutlinedTextField(
                value = expensesAmount?.toString() ?: "",
                onValueChange = { expensesAmount = it },
                hint = "Cost in $ (round to whole number)",
                modifier = Modifier.width(fieldWidth),
            )
            Text(
                text = "Recurring expense?",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
            )
            Checkbox(
                checked = expensesRepeating,
                onCheckedChange = { expensesRepeating = it },
                modifier = Modifier.padding(4.dp),
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
            )
        }
        if (expensesRepeating) {
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                SimpleDropdownMenu(
                    items = FrequencyType.displayNames(),
                    selected = expensesFrequencyType?.name,
                    onItemSelected = { expensesFrequencyType = FrequencyType.fromString(it) },
                    modifier = Modifier.defaultMinSize(minWidth = fieldWidth)
                )
                Spacer(modifier = Modifier.width(4.dp))
                NumberOutlinedTextField(
                    value = expensesFrequencyInterval?.toString() ?: "",
                    onValueChange = { expensesFrequencyInterval = it },
                    hint = "Interval",
                    modifier = Modifier.width(fieldWidth),
                )
            }
        }
    }
}

@Composable
private fun EnergyFields(
    fieldWidth: Dp,
    energy: Energy?,
    onValueChanged: (Energy?) -> Unit,
) {
    var mentalEnergy by remember { mutableStateOf(energy?.mentalCost) }
    var physicalEnergy by remember { mutableStateOf(energy?.physicalCost) }
    remember(mentalEnergy, physicalEnergy) {
        val newEnergy = (energy ?: Energy.EMPTY).copy(
            mentalCost = mentalEnergy,
            physicalCost = physicalEnergy,
        )
        onValueChanged(newEnergy)
    }
    LemiFieldTitle(
        text = "Energy:",
        modifier = Modifier.padding(top = 8.dp)
    )
    Row {
        Column {
            LemiFieldTitle(
                text = "Physical:",
                modifier = Modifier.padding(vertical = 8.dp),
            )
            SimpleDropdownMenu(
                items = EnergyCost.displayNames(),
                selected = energy?.physicalCost?.name,
                onItemSelected = { physicalEnergy = EnergyCost.fromString(it) },
                modifier = Modifier.defaultMinSize(minWidth = fieldWidth),
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            LemiFieldTitle(
                text = "Mental:",
                modifier = Modifier.padding(vertical = 8.dp),
            )
            SimpleDropdownMenu(
                items = EnergyCost.displayNames(),
                selected = energy?.mentalCost?.name,
                onItemSelected = { mentalEnergy = EnergyCost.fromString(it) },
                modifier = Modifier.defaultMinSize(minWidth = fieldWidth),
            )
        }
    }
}