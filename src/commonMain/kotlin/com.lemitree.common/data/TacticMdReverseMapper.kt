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

fun String.extractTitle() = split("# **")[1].split("**").first()

fun String.extractMetadata() = lines().first().split("(")[1].split(")").first()