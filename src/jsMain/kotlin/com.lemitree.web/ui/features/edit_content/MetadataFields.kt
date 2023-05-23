package com.lemitree.web.ui.features.edit_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lemitree.common.data.Energy
import com.lemitree.common.data.EnergyCost
import com.lemitree.common.data.Expenses
import com.lemitree.common.data.Frequency
import com.lemitree.common.data.FrequencyType
import com.lemitree.common.data.TacticMetadata
import com.lemitree.common.data.TimeDuration
import com.lemitree.web.ui.components.Column
import com.lemitree.web.ui.components.DropdownMenu
import com.lemitree.web.ui.components.LemiNumberTextField
import com.lemitree.web.ui.components.Row
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.CheckboxInput
import org.jetbrains.compose.web.dom.Text

@Composable
fun MetadataFields(
    fieldWidth: Int,
    data: TacticMetadata?,
    onMetadataChanged: (TacticMetadata?) -> Unit,
) {
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
        fieldWidth = fieldWidth,
        frequency = frequency,
        onValueChanged = { frequency = it },
    )
    TimeDurationFields(
        fieldWidth = fieldWidth,
        timeDuration = timeDuration,
        onValueChanged = { timeDuration = it },
    )
    ExpensesFields(
        fieldWidth = fieldWidth,
        expenses = expenses,
        onValueChanged = { expenses = it },
    )
    EnergyFields(
        fieldWidth = fieldWidth,
        energy = energy,
        onValueChanged = { energy = it },
    )
}

@Composable
private fun FrequencyFields(
    fieldWidth: Int,
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
    Text("Frequency:")
    Row {
        DropdownMenu(
            items = FrequencyType.displayNames(),
            selected = frequencyType?.name,
            onItemSelected = { frequencyType = FrequencyType.fromString(it) },
            contentStyle = { width(fieldWidth.px) }
        )
        LemiNumberTextField(
            text = frequencyInterval.toString(),
            onValueChanged = { frequencyInterval = it?.toInt() },
            contentStyle = { width(fieldWidth.px) }
        )
    }
}

@Composable
private fun TimeDurationFields(
    fieldWidth: Int,
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
    Text("Time duration:")
    Row {
        LemiNumberTextField(
            hint = "Hours",
            text = durationHours?.toString() ?: "",
            onValueChanged = { durationHours = it?.toInt() },
            contentStyle = { width(fieldWidth.px) }
        )
        LemiNumberTextField(
            hint = "Minutes",
            text = durationMinutes?.toString() ?: "",
            onValueChanged = {
                durationMinutes = it?.let {
                    val value = it.toInt()
                    if (value > 59) 59 else value
                }
            },
            contentStyle = { width(fieldWidth.px) }
        )
    }
}

@Composable
private fun ExpensesFields(
    fieldWidth: Int,
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
        val newExpenses =
            if (expensesAmount != null && expensesFrequency != null)
                Expenses(cost = expensesAmount!!, frequency = expensesFrequency)
            else null
        onValueChanged(newExpenses)
    }
    Text("Expenses:")
    Column {
        Row {
            LemiNumberTextField(
                hint = "Cost in $",
                text = expensesAmount?.toString() ?: "",
                onValueChanged = { expensesAmount = it?.toInt() },
                contentStyle = { width(fieldWidth.px) }
            )
            CheckboxInput {
                style { padding(25.px) }
                checked(expensesRepeating)
                onInput { expensesRepeating = it.value }
            }
        }
        if (expensesRepeating) {
            Row {
                DropdownMenu(
                    items = FrequencyType.displayNames(),
                    selected = expensesFrequencyType?.name,
                    onItemSelected = { expensesFrequencyType = FrequencyType.fromString(it) },
                    contentStyle = { width(fieldWidth.px) }
                )
                LemiNumberTextField(
                    text = expensesFrequencyInterval.toString(),
                    onValueChanged = { expensesFrequencyInterval = it?.toInt() },
                    contentStyle = { width(fieldWidth.px) }
                )
            }
        }
    }
}

@Composable
private fun EnergyFields(
    fieldWidth: Int,
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
    Text("Energy:")
    Row {
        DropdownMenu(
            items = EnergyCost.displayNames(),
            selected = energy?.physicalCost?.name,
            onItemSelected = { physicalEnergy = EnergyCost.fromString(it) },
            contentStyle = { width(fieldWidth.px) }
        )
        DropdownMenu(
            items = EnergyCost.displayNames(),
            selected = energy?.mentalCost?.name,
            onItemSelected = { mentalEnergy = EnergyCost.fromString(it) },
            contentStyle = { width(fieldWidth.px) }
        )
    }
}