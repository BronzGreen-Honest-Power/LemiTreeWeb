package com.lemitree.common.data

import com.lemitree.common.helpers.dropLastToInclusive
import com.lemitree.common.helpers.dropSpacedPrefix
import com.lemitree.common.helpers.dropToInclusive
import com.lemitree.common.helpers.extractBetween
import com.lemitree.common.helpers.trimBlankLines

fun String.toTacticContent() = TacticContent(
    title = extractTitle(),
    infographicLink = extractLink("infographic"),
    videoLink = extractLink("video"),
    audioLink = extractLink("audio"),
    intro = extractTagContent("intro"),
    why = extractSection("why"),
    benefits = extractSubsections("why"),
    how = extractSection("how"),
    instructions = extractInstructions(),
    sources = extractSources(),
)

fun String.extractMetadata() = lines()
    .first()
    .dropToInclusive('(')
    .dropLastToInclusive(')')

private fun String.extractTitle() = extractBetween("# **", "**")

private fun String.extractSources() = extractTagContent("sources")
    .lines()
    .map { sourceString ->
        sourceString
            .dropSpacedPrefix() // "n) "
            .let {
                if ("[" in it) {
                    Source(
                        title = it.dropToInclusive('[')
                            .dropLastToInclusive(']'),
                        link = it.dropToInclusive(']')
                            .dropToInclusive('(')
                            .dropLastToInclusive(')'),
                    )
                }
                else Source(title = it, link = null)
            }
    }

private fun String.extractSection(tag: String) = extractTagContent(tag)
    .split("###")
    .first()
    .lines()
    .trimBlankLines()
    .joinToString("\n")

private fun String.extractSubsections(tag: String) = extractTagContent(tag)
    .lines()
    .run {
        val indexes = filter { "###" in it }
            .map { this.indexOf(it) }
        indexes.mapIndexed { index, subsectionIndex ->
            val endIndex =
                if (index != indexes.lastIndex) indexes[index + 1]
                else this.lastIndex + 1
            this.subList(subsectionIndex + 1, endIndex)
                .joinToString("\n")
                .trimEnd()
        }
    }

private fun String.extractSubsectionTitles(tag: String) = extractTagContent(tag)
    .lines()
    .filter { "###" in it }
    .map { it.extractBetween("### *", "*") }

private fun String.extractInstructions() = run {
    val titles = extractSubsectionTitles("how")
    val subsections = extractSubsections("how")
    val descriptions = subsections.map {  subsection ->
        val line = subsection.lines().find { !it.startsWith("*") }
        line?.ifBlank { "" } ?: ""
    }
    val bulletPoints = subsections.map { subsection ->
        subsection.lines()
            .filter { it.startsWith("*") }
            .map { it.dropSpacedPrefix() }
    }
    titles.mapIndexed { index, title ->
        Instruction(
            title = title,
            text = descriptions[index],
            bulletPoints = bulletPoints[index],
        )
    }
}


private fun String.extractLink(tag: String) = extractTagContent(tag)
    .dropSpacedPrefix() // "* "

private fun String.extractTagContent(tag: String) = lines()
    .run {
        subList(
            fromIndex = indexOfFirst { "BO-$tag" in it },
            toIndex = indexOfFirst { "EO-$tag" in it },
        )
    }
    .drop(1)
    .trimBlankLines()
    .joinToString("\n")


