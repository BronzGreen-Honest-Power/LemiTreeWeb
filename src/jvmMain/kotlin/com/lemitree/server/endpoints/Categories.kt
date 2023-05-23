package com.lemitree.server.endpoints

import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.server.BASE_DIR
import com.lemitree.server.helpers.ioLaunch
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File

suspend fun PipelineContext<Unit, ApplicationCall>.processNewCategory(
    baseDir: String = getKoinInstance(BASE_DIR)
) = processEditCategory(
    baseDir = baseDir,
    editingAllowed = false
)

suspend fun PipelineContext<Unit, ApplicationCall>.processEditCategory(
    baseDir: String = getKoinInstance(BASE_DIR),
    editingAllowed: Boolean = true,
) = ioLaunch {
    val categoryPath = call.receive<String>()
    println("Received category path:\n${categoryPath}")
    val filePath = "${baseDir}/${categoryPath}"
    val newDirectory = File(filePath)
    val newFile = File("${filePath}/.gitkeep")
    val categoryName = newDirectory.toPath().last()
    if (newDirectory.exists() && !editingAllowed)
        call.respond(HttpStatusCode.Conflict, "Category with this name already exists.")
    else {
        println("Creating file: $filePath")
        newDirectory.mkdir()
        @Suppress("BlockingMethodInNonBlockingContext")
        newFile.createNewFile()
    }
    if (newDirectory.exists() && newDirectory.isDirectory)
        createPullRequest(
            baseDir = baseDir,
            branchName = "New_Category-${categoryPath}",
            commitMessage = "New Category: $categoryPath",
            prTitle = "New Category: $categoryName",
        ) || prCreationFailure()
    else call.respond(HttpStatusCode.InternalServerError, "Failed to create the category file.")
    call.respond(HttpStatusCode.Created)
}