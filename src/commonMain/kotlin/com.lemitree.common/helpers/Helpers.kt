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

fun <T> List<T>.insertBetween(item: T): List<T> {
    if (isEmpty()) return this
    val list = mutableListOf(this.first())
    drop(1).forEach { list.addAll(listOf(item, it)) }
    return list
}
