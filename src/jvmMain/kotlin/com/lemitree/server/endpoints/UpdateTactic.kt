package com.lemitree.server.endpoints

import com.lemitree.common.data.ContentAction
import com.lemitree.common.data.Tactic
import com.lemitree.common.data.toMarkdown
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
import java.text.SimpleDateFormat
import java.util.Date

suspend fun PipelineContext<Unit, ApplicationCall>.processModifyTactic() {
    call.parameters["action"]?.let { action ->
        when (ContentAction.fromKey(action)) {
            ContentAction.CREATE -> processEditTactic(editingAllowed = false)
            ContentAction.EDIT -> processEditTactic(editingAllowed = true)
            null -> call.respond(HttpStatusCode.BadRequest, "Invalid 'action' parameter")
        }
    } ?: call.respond(HttpStatusCode.BadRequest, "Missing 'action' parameter")
}

/**
 * Delete existing file if editing
 * Create file with specification
 * Check file created
 * */
private suspend fun PipelineContext<Unit, ApplicationCall>.processEditTactic(
    baseDir: String = getKoinInstance(BASE_DIR),
    editingAllowed: Boolean,
) = ioLaunch {
    val tactic = call.receive<Tactic>()
    println("Received tactic:\n${tactic}")
    val fileContent = tactic.toMarkdown()
    val fileName = tactic.fileName
    val filePath = "${baseDir}/${tactic.path}/$fileName"
    val newFile = File(filePath)
    if (editingAllowed) newFile.delete() //todo catch io exception?
    println("Creating file: $filePath")
    newFile.createNewFile().also { created ->
        if (!created) {
            call.respond(HttpStatusCode.Forbidden, "Tactic with this name already exists.")
            return@ioLaunch
        }
        println("Writing content of $filePath:")
        println(fileContent)
        newFile.writeText(fileContent)
    }
    if (!newFile.exists()) {
        call.respond(HttpStatusCode.InternalServerError, "Failed to create the tactic file.")
        return@ioLaunch
    }
    val gitKeyWord = if (editingAllowed) "Edit" else "New"
    val editTimeStamp = if (editingAllowed) "-${currentTimeFormatted()}" else ""
    createPullRequest(
        baseDir = baseDir,
        branchName = "${gitKeyWord}_Tactic-${tactic.path}/$fileName" + editTimeStamp,
        commitMessage = "$gitKeyWord Tactic: $fileName",
        prTitle = "$gitKeyWord Tactic: ${tactic.tacticName}",
    ) || prCreationFailure() || return@ioLaunch
    call.respond(HttpStatusCode.Created)
}

private fun currentTimeFormatted(): String = Date().let {
    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(it)
}