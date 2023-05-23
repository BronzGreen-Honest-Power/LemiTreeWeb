package com.lemitree.server.endpoints

import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.server.BASE_DIR
import com.lemitree.server.helpers.ioLaunch
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File

suspend fun PipelineContext<Unit, ApplicationCall>.processTactics(
    baseDir: String = getKoinInstance(BASE_DIR),
) {
    val path = call.parameters.getAll("path")?.joinToString("/")
    if (path.isNullOrEmpty()) call.respond("Error: Invalid path")
    println("path: $path")
    // todo: secure against tracing path to other files outside the repo
    ioLaunch {
        val file = File("$baseDir/$path")
        val response = when {
            !file.exists() -> "Error: File not found"
            file.isDirectory -> "Error: This is a directory"
            else -> file.bufferedReader().readText()
        }
        call.respond(response)
    }
}