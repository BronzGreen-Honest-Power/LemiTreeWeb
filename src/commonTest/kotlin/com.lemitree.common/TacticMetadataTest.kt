package com.lemitree.common

import com.lemitree.common.data.Energy
import com.lemitree.common.data.EnergyCost
import com.lemitree.common.data.Expenses
import com.lemitree.common.data.Frequency
import com.lemitree.common.data.FrequencyType
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TacticMetadataTest {

    @Test
    fun testDecodeFrequency() {
        val tag1 = "L"
        val frequency1 = Frequency(
            type = FrequencyType.LIFETIME,
            interval = 0,
        )
        assertEquals(frequency1, Frequency.decode(tag1))

        val tag2 = "D"
        val frequency2 = Frequency(
            type = FrequencyType.DAYS,
            interval = 0,
        )
        assertEquals(frequency2, Frequency.decode(tag2))

        val tag3 = "M6"
        val frequency3 = Frequency(
            type = FrequencyType.MONTHS,
            interval = 6,
        )
        assertEquals(frequency3, Frequency.decode(tag3))
    }

    @Test
    fun testDecodeDuration() { //todo
        // TH2M30
        // TM5
    }

    @Test
    fun testDecodeExpenses() {
        val tag1 = "100"
        val expenses1 = Expenses(
            cost = 100,
            frequency = null,
        )
        assertEquals(expenses1, Expenses.decode(tag1))

        val tag2 = "100W10"
        val expenses2 = Expenses(
            cost = 100,
            frequency = Frequency(
                type = FrequencyType.WEEKS,
                interval = 10,
            ),
        )
        assertEquals(expenses2, Expenses.decode(tag2))
    }

    @Test
    fun testDecodeEnergy() {
        val tag1 = "PLMH"
        val energy1 = Energy(
            physicalCost = EnergyCost.LOW,
            mentalCost = EnergyCost.HIGH,
        )
        assertEquals(energy1, Energy.decode(tag1))

        val tag2 = "MH"
        val energy2 = Energy(
            physicalCost = null,
            mentalCost = EnergyCost.HIGH,
        )
        assertEquals(energy2, Energy.decode(tag2))

        val tag3 = "PA"
        val energy3 = Energy(
            physicalCost = EnergyCost.AVERAGE,
            mentalCost = null,
        )
        assertEquals(energy3, Energy.decode(tag3))
    }
}