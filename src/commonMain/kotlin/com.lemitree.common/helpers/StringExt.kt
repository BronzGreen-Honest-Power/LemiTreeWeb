package com.lemitree.common.helpers

fun String.titlecase() = lowercase().replaceFirstChar { it.titlecase() }

fun String.ifNotEmpty(value: (String) -> String) = if (isNotEmpty()) value(this) else this