package com.lemitree.web

import kotlinx.serialization.Serializable

/**
 * This config comes from your local config.js.
 * If you need to modify the config values, do so in there.
 * Note: The config.js file is in gitignore.
**/

@Serializable
data class FrontendConfig(
    val baseUrl: String,
)