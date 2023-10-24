package com.lemitree.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(backendModule)
    }

    val config = loadBackendConfig()

    embeddedServer(Netty, config.port) {
        pluginSetup()
        routingSetup()
    }.start(wait = true)
}