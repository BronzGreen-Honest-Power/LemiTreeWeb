package com.lemitree.common.data

import com.lemitree.common.helpers.titlecase
import kotlinx.serialization.Serializable

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
    fun isEmpty() = this == EMPTY
}

fun TacticMetadata?.encode(): String {
    this ?: return "U"
    this.isEmpty() && return "U"
    val encodedTagsList = mutableListOf<String>()
    frequency?.let { encodedTagsList.add(it.encode()) }
    timeDuration?.let { encodedTagsList.add(it.encode()) }
    expenses?.let { encodedTagsList.add(it.encode()) }
    energy?.let { encodedTagsList.add(it.encode()) }
    return if (encodedTagsList.isEmpty()) "U"
        else encodedTagsList.joinToString(",")
}

fun String.decodeMetadata(): TacticMetadata? {
    (isEmpty() || equals("U")) && return null
    var metadata = TacticMetadata.EMPTY
    split(",").forEach { tag ->
        val tagData = tag.drop(1)
        when (tag.first().toString()) {
            Frequency.code -> metadata = metadata.copy(frequency = Frequency.decode(tagData))
            Expenses.code -> metadata = metadata.copy(expenses = Expenses.decode(tagData))
            Energy.code -> metadata = metadata.copy(energy = Energy.decode(tagData))
            TimeDuration.code -> metadata = metadata.copy(timeDuration = TimeDuration.decode(tagData))
        }
    }
    return metadata
}

@Serializable
data class Frequency(
    val type: FrequencyType,
    val interval: Int,
) : Metadata {
    companion object {
        const val code = "F"
        fun decode(tag: String) = Frequency(
            type = FrequencyType.fromCode(tag.first().toString()),
            interval = tag.drop(1).toIntOrNull() ?: 0
        )
    }
    override fun encode(): String =
        code + type.code + if (interval > 1) interval else ""
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
        fun fromCode(code: String) = values().first { it.code == code }
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
        fun decode(tag: String) = TimeDuration(
            hours = tag.tagNumValue(hourCode) ?: 0,
            minutes = tag.tagNumValue(minutesCode) ?: 0,
        )
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
        private fun String.frequencyIndex() = indexOfFirst { it.digitToIntOrNull() == null }
        private fun String.frequencyTypeCode() = find { it.digitToIntOrNull() == null }
        fun decode(tag: String) = Expenses(
            cost = tag.tagNumValue() ?: 0,
            frequency = tag.frequencyTypeCode()?.let {
                Frequency(
                    type = FrequencyType.fromCode(it.toString()),
                    interval = (tag.drop(tag.frequencyIndex() + 1)).toIntOrNull() ?: 0,
                )
            }
        )
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
        fun decode(tag: String) = Energy(
            physicalCost = EnergyCost.fromCode(tag.tagCharValue(physicalCode)),
            mentalCost = EnergyCost.fromCode(tag.tagCharValue(mentalCode))
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
        fun fromCode(tag: String?): EnergyCost? = tag?.let { code ->
            values().firstOrNull { code.first().toString() == it.code }
        }
    }

    override val displayText: String
        get() = name.titlecase()
}

private fun String.tagCharValue(code: String): String? {
    val codeIndex = indexOf(code)
        .takeIf { it >= 0 }
        ?: return null
    val valueIndex = codeIndex + 1
    return this[valueIndex].toString()
}

private fun String.tagNumValue(code: String = "no_code"): Int? {
    val codeIndex = indexOf(code)
        .takeIf { it >= 0 }
        .let { if (code == "no_code") -1 else it }
        ?: return null
    return this
        .drop(codeIndex + 1)
        .takeWhile { it.digitToIntOrNull() != null }
        .toIntOrNull()
}