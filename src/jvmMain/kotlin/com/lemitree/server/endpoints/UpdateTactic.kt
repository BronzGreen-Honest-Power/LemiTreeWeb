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

suspend fun PipelineContext<Unit, ApplicationCall>.processNewTactic(
    baseDir: String = getKoinInstance(BASE_DIR)
) {
    processEditTactic(baseDir = baseDir, editingAllowed = false)
}

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
) {
    ioLaunch {
        val tactic = call.receive<Tactic>()
        println("Received tactic:\n$tactic")
        val fileContent = tactic.toMarkdown()
        val fileName = tactic.fileName
        val filePath = "${baseDir}/${tactic.path}/$fileName"
        val newFile = File(filePath)
        if (newFile.exists() && !editingAllowed) {
            call.respond(HttpStatusCode.Conflict, "Tactic with this name already exists.")
        } else {
            withContext(Dispatchers.IO) {
                println("Creating file: $filePath")
                newFile.createNewFile().also {
                    if (it) {
                        println("Writing content of $filePath:")
                        println(fileContent)
                        newFile.writeText(fileContent)
                    }
                }
            }
        }
        if (newFile.exists()) {
            val commitMessage = "\"New Tactic: $fileName\""
            val branchName = "\"New_Tactic-${tactic.tacticName}\""
            // todo: test process against potential concurrency issues
            val successful = File(baseDir).runCommand<Boolean>(
                "git checkout master && " +
                "git checkout -b $branchName && " +
                "git add -A && " +
                "git commit -m $commitMessage && " +
                "git push --set-upstream origin $branchName && " +
                "gh api --method POST " +
                        "-H \"Accept: application/vnd.github+json\" " +
                        "-H \"X-GitHub-Api-Version: 2022-11-28\" " +
                        "/repos/BronzGreen-Honest-Power/LemiTree/pulls " +
                        "-f title='$commitMessage' " +
                        "-f body='$commitMessage' " + //todo make this something more meaningful perhaps
                        "-f head='$branchName' " +
                        "-f base='master' && " +
                "(git checkout master; echo true) || " +
                // todo: change stashing to resetting once the system is tested
                "(git add -A && git stash; git checkout master; echo false)"
            )
            if (successful == false) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to push file to Github.")
            }
        } else call.respond(HttpStatusCode.InternalServerError, "Failed to create the tactic file.")
        call.respond(fileContent)
    }
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