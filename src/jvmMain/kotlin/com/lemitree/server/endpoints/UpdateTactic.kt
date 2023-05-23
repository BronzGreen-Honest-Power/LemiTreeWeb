package com.lemitree.server.endpoints

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

suspend fun PipelineContext<Unit, ApplicationCall>.processNewTactic(
    baseDir: String = getKoinInstance(BASE_DIR)
) = processEditTactic(
    baseDir = baseDir,
    editingAllowed = false
)

/**
 * Create file with specification
 * Check file exists & edit permitted
 * Create & checkout branch
 * Commit changes
 * Push branch with commit
 * Create a PR
 * Checkout master again
 * */
suspend fun PipelineContext<Unit, ApplicationCall>.processEditTactic(
    baseDir: String = getKoinInstance(BASE_DIR),
    editingAllowed: Boolean = true,
) = ioLaunch {
    val tactic = call.receive<Tactic>()
    println("Received tactic:\n${tactic}")
    val fileContent = tactic.toMarkdown()
    val fileName = tactic.fileName
    val filePath = "${baseDir}/${tactic.path}/$fileName"
    val newFile = File(filePath)
    if (newFile.exists() && !editingAllowed)
        call.respond(HttpStatusCode.Conflict, "Tactic with this name already exists.")
    else {
        println("Creating file: $filePath")
        @Suppress("BlockingMethodInNonBlockingContext") // Call is already surrounded by ioLaunch
        newFile.createNewFile().also {
            if (it) {
                println("Writing content of $filePath:")
                println(fileContent)
                newFile.writeText(fileContent)
            }
        }
    }
    if (newFile.exists())
        createPullRequest(
            baseDir = baseDir,
            branchName = "New_Tactic-${tactic.path}/$fileName",
            commitMessage = "New Tactic: $fileName",
            prTitle = "New Tactic: ${tactic.tacticName}}",
        ) || prCreationFailure()
    else call.respond(HttpStatusCode.InternalServerError, "Failed to create the tactic file.")
    call.respond(HttpStatusCode.Created)
}

//gh api \
//  --method POST \
//  -H "Accept: application/vnd.github+json" \
//  -H "X-GitHub-Api-Version: 2022-11-28" \
//  /repos/BronzGreen-Honest-Power/LemiTree/pulls \
//  -f title='Test PR from server' \
// -f body='Please pull these awesome changes in!' \
// -f head='test-branch' \
// -f base='master'