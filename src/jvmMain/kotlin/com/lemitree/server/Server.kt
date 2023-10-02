package com.lemitree.server

import com.lemitree.common.helpers.getKoinInstance
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(backendModule)
    }

    val config = getKoinInstance<BackendConfig>()
    println("Loaded config: $config")

    embeddedServer(Netty, config.port) {
        pluginSetup()
        routingSetup()
    }.start(wait = true)
}