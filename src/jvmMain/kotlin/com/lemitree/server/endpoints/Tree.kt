package com.lemitree.server.endpoints

import com.lemitree.common.data.mapToTreeItems
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.server.BASE_DIR
import com.lemitree.server.helpers.ioLaunch
import com.lemitree.server.helpers.runListCommand
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File

suspend fun PipelineContext<Unit, ApplicationCall>.processTree(
    baseDir: String = getKoinInstance(BASE_DIR),
) {
    ioLaunch {
        val tree = File(baseDir)
            .runListCommand("du -a")
            .dropLast(1)
            .filter { !it.contains(".git") }
            .map { it.split("./").last() }
        println("Tree: $tree")
        val mappedTree = tree
            .mapToTreeItems()
        println("Tree: $mappedTree")
        call.respond(mappedTree)
    }
}
