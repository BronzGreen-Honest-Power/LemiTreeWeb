package com.lemitree.server.endpoints

import com.lemitree.common.data.Tactic
import com.lemitree.common.data.toMarkdown
import com.lemitree.common.helpers.getKoinInstance
import com.lemitree.server.BASE_DIR
import com.lemitree.server.helpers.ioLaunch
import com.lemitree.server.helpers.runCommand
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Create file with specification
 * Check file exists
 * Create & checkout branch
 * Commit changes
 * Push branch with commit
 * Create a PR
 * Checkout master again
 * */
suspend fun PipelineContext<Unit, ApplicationCall>.processNewTactic(
    baseDir: String = getKoinInstance(BASE_DIR),
) {
    ioLaunch {
        val tactic = call.receive<Tactic>()
        val fileContent = tactic.toMarkdown()
        val tacticName = tactic.content.title.replace(' ', '_')
        val fileName = "${tacticName}.md"
        val filePath = "${baseDir}/${tactic.path}/$fileName"
        val newFile = File(filePath)
        if (newFile.exists()) {
            call.respond(HttpStatusCode.Conflict, "Tactic with this name already exists.")
        } else {
            withContext(Dispatchers.IO) {
                newFile.createNewFile().also {
                    if (it) newFile.writeText(fileContent)
                }
            }
        }
        if (newFile.exists()) {
            val commitMessage = "\"New Tactic: ${fileName}\""
            val branchName = "\"New_Tactic-${tacticName}\""
            val successful = File(baseDir).runCommand<Boolean>(
                "git checkout master && " +
                "git checkout -b $branchName && " +
                "git add -A && " +
                "git commit -m $commitMessage && " +
                "git push --set-upstream origin $branchName && " +
//                "gh pr create --base my-base-branch --head $branchName --title $commitMessage && " +
                //todo create a pr
                "(git checkout master; echo true) || " +
                "(git reset --hard HEAD; git checkout master; echo false)"
            )
            if (successful == false) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to push file to Github.")
            }
        } else call.respond(HttpStatusCode.InternalServerError, "Failed to create the tactic file.")
        call.respond(fileContent)
    }
}