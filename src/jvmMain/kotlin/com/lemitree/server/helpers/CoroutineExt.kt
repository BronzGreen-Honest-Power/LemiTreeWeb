package com.lemitree.server.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun ioLaunch(block: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        launch(Dispatchers.IO) {
            block()
        }
    }
}