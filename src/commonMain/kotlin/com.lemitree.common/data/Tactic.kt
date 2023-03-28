package com.lemitree.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Tactic(
    val path: String,
    val content: TacticContent,
    val metadata: TacticMetadata?,
)

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
)
typealias Benefit = String

@Serializable
data class Instruction(
    val title: String,
    val text: String,
    val bulletPoints: List<BulletPoint>,
)
typealias BulletPoint = String

@Serializable
data class Source(
    val title: String,
    val link: String?,
)