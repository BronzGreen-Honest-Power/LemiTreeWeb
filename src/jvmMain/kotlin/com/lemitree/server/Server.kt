package com.lemitree.server

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin
import org.koin.ktor.ext.inject

data class Sesame(val open: String)

fun main() {
    startKoin {
        modules(backendModule)
    }

    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
        val x: Sesame by inject()
        println("Test injection into Ktor: ${x.open}")
        pluginSetup()
        routingSetup()
    }.start(wait = true)
}
