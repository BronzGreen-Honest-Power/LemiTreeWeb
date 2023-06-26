package com.lemitree.common.data

fun String.toTacticContent(): TacticContent {

    return TacticContent(
        title = "",
        infographicLink = null ?: "",
        videoLink = null ?: "",
        audioLink = null ?: "",
        intro = "",
        why = "",
        benefits = emptyList(),
        how = "",
        instructions = emptyList(),
        sources = emptyList(),
    )
}

private fun String.extractTitle() = first { startsWith("# **") && endsWith("**") }