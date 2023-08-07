package com.lemitree.common

import com.lemitree.common.data.Energy
import com.lemitree.common.data.EnergyCost
import com.lemitree.common.data.Expenses
import com.lemitree.common.data.Frequency
import com.lemitree.common.data.FrequencyType
import com.lemitree.common.data.TacticMetadata
import com.lemitree.common.data.TimeDuration
import com.lemitree.common.data.decodeMetadata
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TacticMetadataTest {

    @Test
    fun testDecodeMetadata() {
        // [FL, TH2M30, X100W10, EPLMH]
        val metadataString1 = "FL,TH2M30,X100W10,EPLMH"
        val metadata1 = TacticMetadata(
            frequency = Frequency(
                type = FrequencyType.LIFETIME,
                interval = 0,
            ),
            timeDuration = TimeDuration(
                hours = 2,
                minutes = 30,
            ),
            expenses = Expenses(
                cost = 100,
                frequency = Frequency(
                    type = FrequencyType.WEEKS,
                    interval = 10,
                ),
            ),
            energy = Energy(
                physicalCost = EnergyCost.LOW,
                mentalCost = EnergyCost.HIGH,
            ),
        )
        assertEquals(metadata1, metadataString1.decodeMetadata())

        // [FM6, EPA]
        val metadataString2 = "FM6,EPA"
        val metadata2 = TacticMetadata(
            frequency = Frequency(
                type = FrequencyType.MONTHS,
                interval = 6,
            ),
            energy = Energy(
                physicalCost = EnergyCost.AVERAGE,
                mentalCost = null,
            ),
            timeDuration = null,
            expenses = null,
        )
        assertEquals(metadata2, metadataString2.decodeMetadata())

        // [U]
        val metadataString3 = "U"
        val metadata3 = null
        assertEquals(metadata3, metadataString3.decodeMetadata())

        // empty - should never happen
        val metadataString4 = ""
        val metadata4 = null
        assertEquals(metadata4, metadataString4.decodeMetadata())
    }

    @Test
    fun testDecodeFrequency() {
        // FL
        val tag1 = "L"
        val frequency1 = Frequency(
            type = FrequencyType.LIFETIME,
            interval = 0,
        )
        assertEquals(frequency1, Frequency.decode(tag1))

        // FD
        val tag2 = "D"
        val frequency2 = Frequency(
            type = FrequencyType.DAYS,
            interval = 0,
        )
        assertEquals(frequency2, Frequency.decode(tag2))

        //FM6
        val tag3 = "M6"
        val frequency3 = Frequency(
            type = FrequencyType.MONTHS,
            interval = 6,
        )
        assertEquals(frequency3, Frequency.decode(tag3))
    }

    @Test
    fun testDecodeDuration() {
        // TH2M30
        val tag1 = "H2M30"
        val duration1 = TimeDuration(
            hours = 2,
            minutes = 30,
        )
        assertEquals(duration1, TimeDuration.decode(tag1))

        // TM5
        val tag2 = "TM5"
        val duration2 = TimeDuration(
            hours = 0,
            minutes = 5,
        )
        assertEquals(duration2, TimeDuration.decode(tag2))
    }

    @Test
    fun testDecodeExpenses() {
        // X100
        val tag1 = "100"
        val expenses1 = Expenses(
            cost = 100,
            frequency = null,
        )
        assertEquals(expenses1, Expenses.decode(tag1))

        // X100W10
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
        // EPLMH
        val tag1 = "PLMH"
        val energy1 = Energy(
            physicalCost = EnergyCost.LOW,
            mentalCost = EnergyCost.HIGH,
        )
        assertEquals(energy1, Energy.decode(tag1))

        // EMH
        val tag2 = "MH"
        val energy2 = Energy(
            physicalCost = null,
            mentalCost = EnergyCost.HIGH,
        )
        assertEquals(energy2, Energy.decode(tag2))

        // EPA
        val tag3 = "PA"
        val energy3 = Energy(
            physicalCost = EnergyCost.AVERAGE,
            mentalCost = null,
        )
        assertEquals(energy3, Energy.decode(tag3))
    }
}