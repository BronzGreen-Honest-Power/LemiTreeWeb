package com.lemitree.common.helpers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

inline fun <reified T> getKoinInstance(): T = object : KoinComponent {
    val value: T by inject()
}.value