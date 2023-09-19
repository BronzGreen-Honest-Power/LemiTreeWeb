package com.lemitree.common.data

enum class ContentAction(val key: String) {
    CREATE("create"),
    EDIT("edit");

    companion object {
        fun fromKey(key: String) = entries.find { it.key == key }
    }
}