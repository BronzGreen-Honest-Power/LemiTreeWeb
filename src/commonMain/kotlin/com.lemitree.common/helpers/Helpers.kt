package com.lemitree.common.helpers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

inline fun <reified T> getKoinInstance(): T = object : KoinComponent {
    val value: T by inject()
}.value

inline fun <reified T> getKoinInstance(name: String): T = object : KoinComponent {
    val value: T by inject(named(name))
}.value