package com.lemitree.common.data

import com.lemitree.common.helpers.insertBetween

fun Tactic.toMarkdown(): String =
"""
[//]: <> (${metadata.encode()})

# **${content.title}**

## **Infographic**
[//]: <> (BO-infographic)
- ${content.infographicLink ?: "Link to image or formatted image."}

[//]: <> (EO-infographic)
## **Video**
[//]: <> (BO-video)
- ${content.videoLink ?: "Link to video."}

[//]: <> (EO-video)
## **Audio**
[//]: <> (BO-audio)
- ${content.audioLink ?: "Link to audio."}

[//]: <> (EO-audio)
## **Intro**
[//]: <> (BO-intro)
${content.intro}

[//]: <> (EO-intro)
## **Why**
[//]: <> (BO-why)
${content.why}

${content.benefits.benefitMarkdown()}

[//]: <> (EO-why)
## **How**
[//]: <> (BO-how)
${content.how}

${content.instructions.instructionsMarkdown()}

[//]: <> (EO-how)
## **Sources**
[//]: <> (BO-sources)
${content.sources.sourcesMarkdown()}

[//]: <> (EO-sources)
""".trimIndent()

private fun List<Benefit>.benefitMarkdown() = mapIndexed { index, benefit ->
"""
### *Benefit ${index + 1}*
$benefit
""".trimIndent()
}.insertBetween("\n\n").joinToString("")

private fun List<Instruction>.instructionsMarkdown() = map {
"""
### *${it.title}*
${it.text}
${it.bulletPoints.bulletPointsMarkdown()}
""".trimIndent()
}.insertBetween("\n").joinToString("")

private fun List<BulletPoint>.bulletPointsMarkdown() = map {
    "- $it"
}.insertBetween("\n").joinToString("")

private fun List<Source>.sourcesMarkdown() = mapIndexed { index, source ->
    if (source.link == null) "- (${index + 1}) ${source.title}"
    else "- (${index + 1}) [${source.title}](${source.link})"
}.insertBetween("\n").joinToString("")