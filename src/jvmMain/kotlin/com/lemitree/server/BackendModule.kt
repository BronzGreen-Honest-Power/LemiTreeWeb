package com.lemitree.server

import com.lemitree.server.helpers.readFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_DIR = "BASE_DIR"

val backendModule = module {
    single { Json.decodeFromString<BackendConfig>(readFile("backend_config.json")) }
    single(named(BASE_DIR)) {
        get<BackendConfig>().baseDir
    }
}