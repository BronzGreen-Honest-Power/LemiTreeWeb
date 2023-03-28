package com.lemitree.web

import com.lemitree.common.data.Instruction
import com.lemitree.common.data.Source
import com.lemitree.common.data.Tactic
import com.lemitree.common.data.TacticContent
import com.lemitree.common.data.TreeItem
import com.lemitree.web.data.createTactic
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
    private val _tree = MutableStateFlow<List<TreeItem>>(emptyList())
    val tree: StateFlow<List<TreeItem>> = _tree
    private val _path = MutableStateFlow<String?>(null)
    val selectedPath: StateFlow<String?> = _path
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
            it ?: return@collect
            if (it.endsWith(".md")) fetchContent(it)
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

    fun create(title: String) = scope.launch {
        val newTactic = Tactic(
            path = "Having_-_Resources_-_Means_to_Live/Money",
            content = TacticContent(
                title = title,
                intro = "This is just a test tactic generated from code.",
                why = "Because we need to test the API, of course.",
                benefits = listOf("Well, if it works, the project is one step closer to being live."),
                how = "Via the magic of programming.",
                instructions = listOf(Instruction("Instruction 1", "Well, if you're reading this then it works and nothing else to be done.", listOf("Brag", "Brag even more"))),
                sources = listOf(Source("Source title example", "https://lemitree.com"))
            ),
            metadata = null,
        )
        createTactic(newTactic)
    }
}