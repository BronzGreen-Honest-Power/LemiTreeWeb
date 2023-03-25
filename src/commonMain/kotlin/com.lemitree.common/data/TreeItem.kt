package com.lemitree.common.data

import kotlinx.serialization.Serializable

@Serializable
data class TreeItem(
    val path: String,
    val children: List<TreeItem> = emptyList(),
) {
    val lastSegment get() = path.split("/").last()
    val displayName get() = lastSegment.replace('_', ' ')
    fun hasChildren() = children.isNotEmpty()
}

fun List<String>.mapToTreeItems(): List<TreeItem> {
    if (isEmpty()) return emptyList()
    val topLevelDirs = groupBy { it.count { char -> char == '/' } }
        .minBy { it.key }
        .value
    return topLevelDirs.map { topDir ->
        TreeItem(
            path = topDir,
            children = this.filter { path -> path.startsWith(topDir) && path != topDir }
                .mapToTreeItems()
        )
    }
}