package com.lemitree.common.data

import com.lemitree.common.helpers.titlecase
import kotlinx.serialization.Serializable

// todo: write tests

interface Metadata {
    fun encode(): String
}

@Serializable
data class TacticMetadata(
    val frequency: Frequency?,
    val timeDuration: TimeDuration?,
    val expenses: Expenses?,
    val energy: Energy?,
) {
    companion object {
        val EMPTY get() = TacticMetadata(
            frequency = null,
            timeDuration = null,
            expenses = null,
            energy = null,
        )
    }
}

fun TacticMetadata?.encode(): String {
    this ?: return "U"
    val encodedTagsList = mutableListOf<String>()
    frequency?.let { encodedTagsList.add(it.encode()) }
    timeDuration?.let { encodedTagsList.add(it.encode()) }
    expenses?.let { encodedTagsList.add(it.encode()) }
    energy?.let { encodedTagsList.add(it.encode()) }
    return if (encodedTagsList.isEmpty()) "U"
        else encodedTagsList.joinToString(",")
}

@Serializable
data class Frequency(
    val type: FrequencyType,
    val interval: Int,
) : Metadata {
    companion object {
        const val code = "F"
    }
    override fun encode(): String =
        code + type.code + interval
}

enum class FrequencyType(val code: String) : DropdownItem {
    LIFETIME("L"),
    DAYS("D"),
    WEEKS("W"),
    MONTHS("M"),
    QUARTERS("Q"),
    YEARS("Y");

    companion object {
        fun fromString(s: String): FrequencyType = values().first { it.name.uppercase() == s.uppercase() }
        fun displayNames() = values().map { it.displayText }
        fun names() = values().map { it.name }
    }

    override val displayText: String
        get() = name.titlecase()
}

@Serializable
data class TimeDuration(
    val hours: Int,
    val minutes: Int,
) : Metadata {
    companion object {
        const val code = "T"
        const val hourCode = "H"
        const val minutesCode = "M"
    }
    override fun encode(): String {
        val hoursEncoded = if (hours <= 0) "" else hourCode + hours
        val minutesEncoded = if (minutes <= 0) "" else minutesCode + minutes
        return code + hoursEncoded + minutesEncoded
    }
}

@Serializable
data class Expenses(
    val cost: Int,
    val frequency: Frequency?,
) : Metadata {
    companion object {
        const val code = "X"
    }
    override fun encode(): String {
        val frequencyEncoded = if (frequency == null) "" else frequency.type.code + frequency.interval
        return code + cost + frequencyEncoded
    }
}

@Serializable
data class Energy(
    val physicalCost: EnergyCost?,
    val mentalCost: EnergyCost?,
) : Metadata {
    companion object {
        const val code = "E"
        const val physicalCode = "P"
        const val mentalCode = "M"
        val EMPTY get() = Energy(
            physicalCost = null,
            mentalCost = null,
        )
    }
    override fun encode(): String {
        val physicalEncoded = if (physicalCost == null) "" else physicalCode + physicalCost.code
        val mentalEncoded = if (mentalCost == null) "" else mentalCode + mentalCost.code
        return code + physicalEncoded + mentalEncoded
    }
}

enum class EnergyCost(val code: String) : DropdownItem {
    LOW("L"),
    AVERAGE("A"),
    HIGH("H");

    companion object {
        fun fromString(s: String): EnergyCost = values().first { it.name.uppercase() == s.uppercase() }
        fun displayNames() = values().map { it.displayText }
        fun names() = values().map { it.name }
    }

    override val displayText: String
        get() = name.titlecase()
}