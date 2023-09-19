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
            processTree()
        }
        get("/tactics/{path...}") {
            processTactics()
        }
        post("/tactic/{action}") {
            processModifyTactic()
        }
        post("/category") {
            processNewCategory() //todo allow editing existing
        }
        //todo error reporting
        //todo analytics
    }
}