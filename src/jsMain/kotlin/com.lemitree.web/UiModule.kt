package com.lemitree.web

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.skiko.ClipboardManager
import org.koin.dsl.module

val uiModule = module {
    single<CoroutineContext> { EmptyCoroutineContext }
    single { CoroutineScope(get()) }
    single { ViewModel(get()) }
    single { ClipboardManager() }
    single { loadFrontendConfig() }
}