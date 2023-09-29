package com.lemitree.common.data

import com.lemitree.common.helpers.ifNotEmpty
import com.lemitree.common.helpers.replaceSpaces
import kotlinx.serialization.Serializable

@Serializable
data class Tactic(
    val path: String,
    val content: TacticContent,
    val metadata: TacticMetadata?,
) {
    companion object {
        val EMPTY get() = Tactic(
            path = "",
            content = TacticContent.EMPTY,
            metadata = TacticMetadata.EMPTY,
        )
    }
    val tacticName get() = content.title.replaceSpaces()
    val fileName get() = tacticName.ifNotEmpty { "$it.md" }
}

@Serializable
data class TacticContent(
    val title: String,
    val infographicLink: String? = null,
    val videoLink: String? = null,
    val audioLink: String? = null,
    val intro: String,
    val why: String,
    val benefits: List<Benefit>,
    val how: String,
    val instructions: List<Instruction>,
    val sources: List<Source>,
) {
    companion object {
        val EMPTY get() = TacticContent(
            title = "",
            intro = "",
            why = "",
            benefits = emptyList(),
            how = "",
            instructions = emptyList(),
            sources = emptyList(),
        )
    }
}

typealias Benefit = String

@Serializable
data class Instruction(
    val title: String,
    val text: String,
    val bulletPoints: List<BulletPoint>,
) {
    companion object {
        val EMPTY get() = Instruction(
            title = "",
            text = "",
            bulletPoints = emptyList(),
        )
    }
}
typealias BulletPoint = String

@Serializable
data class Source(
    val title: String,
    val link: String?,
) {
    companion object {
        val EMPTY get() = Source(
            title = "",
            link = null,
        )
    }
}