package com.lemitree.server

import com.lemitree.common.helpers.getKoinInstance
import kotlinx.serialization.Serializable
import java.io.File

/**
 * This config comes from your local LemiTreeWeb/backend_config.json.
 * If you need to modify the config values, do so in there.
 * Note: The backend_config.json file is in gitignore.
 **/

@Serializable
data class BackendConfig(
    val baseDir: String,
    val port: Int,
)

fun loadBackendConfig(): BackendConfig {
    File("backend_config.json").exists() || error("LemiTreeWeb/backend_config.json file not found.")
    val config = try { getKoinInstance<BackendConfig>() } catch (e: Exception) {
        error("backend_config.json appears to be missing some fields. Compare your json file with the BackendConfig class.")
    }
    println("Loaded config: $config")
    return config
}