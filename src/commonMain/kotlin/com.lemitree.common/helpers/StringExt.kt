package com.lemitree.common.helpers

fun String.titlecase() = lowercase().replaceFirstChar { it.titlecase() }

fun String.ifNotEmpty(value: () -> String) = if (isNotEmpty()) value() else this

fun String.ifNotEmpty(block: () -> Unit) { if (isNotEmpty()) block() }