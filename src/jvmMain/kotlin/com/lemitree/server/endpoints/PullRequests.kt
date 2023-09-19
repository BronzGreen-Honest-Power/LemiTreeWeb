package com.lemitree.server.endpoints

import com.lemitree.server.helpers.runCommand
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.io.File

/**
 * Create & checkout branch
 * Commit changes
 * Push branch with commit
 * Create a PR
 * Checkout master again
 * */
fun createPullRequest(// todo: test process against potential concurrency issues
    baseDir: String,
    branchName: String,
    commitMessage: String,
    prTitle: String,
) = File(baseDir).runCommand<Boolean>(
    // todo: this command is a bit long, consider extracting it to a proper script
    "git checkout master && " +
    "git checkout -b \"$branchName\" && " +
    "git add -A && " +
    "git commit -m \"$commitMessage\" && " +
    "git push --set-upstream origin \"$branchName\" && " +
    "gh api --method POST " + // todo: this method will return 0 even if unsuccessful, validate response
    "-H \"Accept: application/vnd.github+json\" " +
    "-H \"X-GitHub-Api-Version: 2022-11-28\" " +
    "/repos/BronzGreen-Honest-Power/LemiTree/pulls " +
    "-f title=\"$prTitle\" " +
    "-f body=\"$commitMessage\" " + //todo make this something more meaningful perhaps
    "-f head=\"$branchName\" " +
    "-f base=\"master\" && " +
    "(git checkout master; echo true) || " +
    // todo: change stashing to resetting once the system is tested
    "(git add -A && git stash; git checkout master; echo false)"
) ?: false

suspend fun PipelineContext<Unit, ApplicationCall>.prCreationFailure(): Boolean {
    call.respond(HttpStatusCode.InternalServerError, "Failed to push file to Github.")
    return false
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
