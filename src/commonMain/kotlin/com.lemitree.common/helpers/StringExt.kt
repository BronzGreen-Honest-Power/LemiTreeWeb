package com.lemitree.common.helpers

fun String.titlecase() = lowercase().replaceFirstChar { it.titlecase() }

fun String.ifNotEmpty(value: (String) -> String) = if (isNotEmpty()) value(this) else this

fun String.extractBetween(start: String, end: String) = split(start)[1].split(end).first()

fun String.dropTo(char: Char) = dropWhile { c -> c != char }

fun String.dropToInclusive(char: Char) = dropTo(char).drop(1)

fun String.dropLastTo(char: Char) = dropLastWhile { c -> c != char }

fun String.dropLastToInclusive(char: Char) = dropLastTo(char).dropLast(1)

fun String.dropSpacedPrefix() = dropToInclusive(' ')

fun List<String>.trimBlankLines() = dropWhile { it.isBlank() }
    .dropLastWhile { it.isBlank() }

fun String.dropLastPathSegment() = split("/").dropLast(1).joinToString("/")