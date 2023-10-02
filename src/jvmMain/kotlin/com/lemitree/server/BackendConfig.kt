package com.lemitree.server

import kotlinx.serialization.Serializable

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