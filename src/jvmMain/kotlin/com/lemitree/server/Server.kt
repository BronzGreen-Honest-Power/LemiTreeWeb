package com.lemitree.server

import com.lemitree.server.endpoints.processTactics
import com.lemitree.server.endpoints.processTree
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.ext.inject

val mod = module {
    single { Sesame("quackz") }
}
data class Sesame(val open: String)

fun main() {
    startKoin {
        modules(mod)
    }
    val baseDir = System.getenv("BASE_DIR_LEMITREE") ?: error("BASE_DIR_LEMITREE env not found!")

    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/") {
                call.respond(HttpStatusCode.BadRequest)
            }
            val x: Sesame by inject()
            println("AAAAAAAAAAA: ${x.open}")
            get("/tree") {
                processTree(baseDir)
            }
            get("/tactics/{path...}") {
                processTactics(baseDir)
            }
        }
    }.start(wait = true)
}
