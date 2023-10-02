package com.lemitree.server.helpers

import java.io.File

fun readFile(path: String) = File(path).let {
    when {
        !it.exists() -> "Error: File not found"
        it.isDirectory -> "Error: This is a directory"
        else -> it.bufferedReader().readText()
    }
}