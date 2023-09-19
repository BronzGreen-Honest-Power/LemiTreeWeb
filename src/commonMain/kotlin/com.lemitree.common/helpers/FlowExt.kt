package com.lemitree.common.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.subscribed(scope: CoroutineScope, initialValue: T) =
    stateIn(scope, SharingStarted.WhileSubscribed(5000L), initialValue)