package com.lemitree.server

import com.lemitree.server.endpoints.processModifyTactic
import com.lemitree.server.endpoints.processNewCategory
import com.lemitree.server.endpoints.processTactics
import com.lemitree.server.endpoints.processTree
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.routingSetup() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.BadRequest)
        }
        get("/tree") {
            /** Response: List of [com.lemitree.common.data.TreeItem] */
            processTree()
        }
        get("/tactics/{path...}") {
            /** Response: Raw Markdown data, convertible to [com.lemitree.common.data.Tactic] */
            processTactics()
        }
        post("/tactic/{action}") {
            /**
             * Body: [com.lemitree.common.data.Tactic]
             * Path argument: [com.lemitree.common.data.ContentAction]
             * */
            processModifyTactic()
        }
        post("/category") {
            /**
             * Body: String (path)
             * */
            processNewCategory() //todo allow editing existing
        }
        //todo error reporting
        //todo analytics
    }
}