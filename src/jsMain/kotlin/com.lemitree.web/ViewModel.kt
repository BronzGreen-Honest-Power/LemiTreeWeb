package com.lemitree.web

import com.lemitree.web.data.getContent
import com.lemitree.web.data.getTree
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val scope: CoroutineScope,
) {
    private val _tree = MutableStateFlow<List<String>>(emptyList())
    val tree: StateFlow<List<String>> = _tree
    private val _path = MutableStateFlow<String>("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Caffeine_before_bed.md")
    private val _mdText = MutableStateFlow<String?>(null)
    val mdText: StateFlow<String?> = _mdText
    init {
        scope.launch {
            delay(500) // so that getBaseUrl is defined
            fetchTree()
            watchPath()
        }
    }

    private suspend fun watchPath() {
        _path.collect {
            fetchContent(it)
        }
    }

    suspend fun fetchContent(path: String) {
        _mdText.update { null }
        _mdText.update { getContent(path) }
    }

    suspend fun fetchTree() {
        _tree.update { getTree() }
    }

    fun updatePath(path: String) = _path.update { path }
}