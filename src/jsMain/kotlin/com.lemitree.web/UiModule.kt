package com.lemitree.web

import com.lemitree.web.util.getConfig
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.skiko.ClipboardManager
import org.koin.dsl.module

val uiModule = module {
    single<CoroutineContext> { EmptyCoroutineContext }
    single { CoroutineScope(get()) }
    single { ViewModel(get()) }
    single { ClipboardManager() }
    single { Json.decodeFromString<FrontendConfig>(JSON.stringify(getConfig())) }
}