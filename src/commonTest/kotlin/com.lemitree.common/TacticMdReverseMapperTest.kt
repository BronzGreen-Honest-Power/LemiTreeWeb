package com.lemitree.common

import com.lemitree.common.data.Instruction
import com.lemitree.common.data.Source
import com.lemitree.common.data.TacticContent
import com.lemitree.common.data.extractMetadata
import com.lemitree.common.data.toTacticContent
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TacticMdReverseMapperTest {

    @Test
    fun testMapMdStringToTacticContent() {
        assertEquals(
            actual = exampleTacticMd.toTacticContent(),
            expected = TacticContent(
                title = "Nasal breathing at night",
                infographicLink = "Link to image or formatted image.",
                videoLink = "Link to video.",
                audioLink = "Link to audio.",
                intro = "Introduction to the tactic.",
                why = "Description of why.\n\nFurther description of why.",
                benefits = listOf(
                    "Description of benefit 1.",
                    "Description of benefit 2.",
                    "Description of benefit 3.",
                ),
                how = "Description of how in bullet points for 1 or more descriptions. Maximum 7 steps/descriptions, otherwise tactic becomes a new domain, see: [Contributor Guide](contributor_guide.md).",
                instructions = listOf(
                    Instruction(
                        title = "Instruction 1",
                        text = "Instruction 1 description.",
                        bulletPoints = listOf(
                            "Step 1",
                            "Step 2",
                            "...",
                        )
                    ),
                    Instruction(
                        title = "Instruction 2",
                        text = "",
                        bulletPoints = listOf(
                            "Step 1",
                            "Step 2",
                            "...",
                        )
                    ),
                ),
                sources = listOf(
                    Source("Test test", null),
                    Source("Testttt", "https://lol.co.uk")
                ),
            )
        )
    }

    @Test
    fun testExtractMetadata() {
        assertEquals(
            expected = "FL, X100D10, EPLMH",
            actual = exampleTacticMd.extractMetadata(),
        )
    }

}

private val exampleTacticMd = """
[//]: <> (FL, X100D10, EPLMH)

# **Nasal breathing at night**

## **Infographic**
[//]: <> (BO-infographic)
- Link to image or formatted image.

[//]: <> (EO-infographic)
## **Video**
[//]: <> (BO-video)
- Link to video.

[//]: <> (EO-video)
## **Audio**
[//]: <> (BO-audio)
- Link to audio.

[//]: <> (EO-audio)
## **Intro**
[//]: <> (BO-intro)
Introduction to the tactic.

[//]: <> (EO-intro)
## **Why**
[//]: <> (BO-why)
Description of why.

Further description of why.

### *Benefit 1*
Description of benefit 1.

### *Benefit 2*
Description of benefit 2.

### *Benefit 3*
Description of benefit 3.

[//]: <> (EO-why)
## **How**
[//]: <> (BO-how)
Description of how in bullet points for 1 or more descriptions. Maximum 7 steps/descriptions, otherwise tactic becomes a new domain, see: [Contributor Guide](contributor_guide.md).

### *Instruction 1*
Instruction 1 description.

- Step 1
- Step 2
- ...

### *Instruction 2*

- Step 1
- Step 2
- ...

[//]: <> (EO-how)
## **Sources**
[//]: <> (BO-sources)
- (1) Test test
- (2) [Testttt](https://lol.co.uk)

[//]: <> (EO-sources)
""".trimIndent()

