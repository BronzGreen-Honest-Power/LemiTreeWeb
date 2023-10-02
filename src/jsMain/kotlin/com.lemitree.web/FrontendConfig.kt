package com.lemitree.web

import kotlinx.serialization.Serializable

/**
 * This config comes from your local resources/frontend_config.json.
 * If you need to modify the config values, do so in there.
 * Note: The frontend_config.json file is in gitignore.
**/

@Serializable
data class FrontendConfig(
    val baseUrl: String,
)