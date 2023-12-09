package com.lemitree.web

import com.lemitree.web.util.getConfig
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * This config comes from your local resources/frontend_config.json.
 * If you need to modify the config values, do so in there.
 * Note: The frontend_config.json file is in gitignore.
**/

@Serializable
data class FrontendConfig(
    val baseUrl: String,
)

fun loadFrontendConfig(): FrontendConfig {
    val config = try {
        val jsConfig = getConfig()
        Json.decodeFromString<FrontendConfig>(JSON.stringify(jsConfig))
    } catch (e: Exception) {
        error("resources/frontend_config.json file is malformed or missing.")
    }
    return config
}