@file:JsModule("./config.js")
package com.lemitree.web.util

/**
* JS functions to be called in Kotlin.
* Must match function name in js file.
* Don't call directly in composables.
*/

external fun getConfig(): String
