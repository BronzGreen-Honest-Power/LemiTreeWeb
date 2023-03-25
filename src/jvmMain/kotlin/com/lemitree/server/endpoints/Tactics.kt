package com.lemitree.server.endpoints

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun PipelineContext<Unit, ApplicationCall>.processTactics(
    baseDir: String,
) {
    val path = call.parameters.getAll("path")?.joinToString("/")
    if (path.isNullOrEmpty()) call.respond("Error: Invalid path")
    println("path: $path")
    // todo: secure against tracing path to other files outside of the repo
    coroutineScope {
        launch(Dispatchers.IO) {
            val file = File("$baseDir/$path")
            val response = when {
                !file.exists() -> "Error: File not found"
                file.isDirectory -> "Error: This is a directory"
                else -> file.bufferedReader().readText()
            }
            call.respond(response)
        }
    }
}