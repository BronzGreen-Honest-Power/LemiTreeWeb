package com.lemitree.web

import com.lemitree.common.data.ContentAction
import com.lemitree.common.data.Tactic
import com.lemitree.common.data.TacticContent
import com.lemitree.common.data.TreeItem
import com.lemitree.common.data.decodeMetadata
import com.lemitree.common.data.extractMetadata
import com.lemitree.common.data.toTacticContent
import com.lemitree.common.helpers.dropLastPathSegment
import com.lemitree.common.helpers.isMdFile
import com.lemitree.common.helpers.subscribed
import com.lemitree.web.data.getContent
import com.lemitree.web.data.getTree
import com.lemitree.web.data.modifyCategory
import com.lemitree.web.data.modifyTactic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val scope: CoroutineScope,
) {
    private val _tree = MutableStateFlow<List<TreeItem>>(emptyList())
    val tree: StateFlow<List<TreeItem>> = _tree
    private val selectedPath = MutableStateFlow<String?>(null)
    val selectedDirectory: StateFlow<String?> = selectedPath
        .map {
            if (it.isMdFile())
                it?.dropLastPathSegment()
            else it
        }
        .subscribed(scope, null)
    private val _mdText = MutableStateFlow<String?>(null)
    val mdText: StateFlow<String?> = _mdText
    private val _isEditingTactic = MutableStateFlow(false)
    val isEditingTactic: StateFlow<Boolean> = _isEditingTactic
    val tactic: StateFlow<Tactic> = mdText
        .combine(selectedPath) { md, path ->
            path.isNullOrEmpty() && return@combine Tactic.EMPTY
            Tactic(
                path = path!!,
                content = md?.toTacticContent() ?: TacticContent.EMPTY,
                metadata = md?.extractMetadata()?.decodeMetadata(),
            )
        }
        .subscribed(scope, Tactic.EMPTY)

    init {
        scope.launch {
            fetchTree()
            watchPath()
        }
    }

    private suspend fun watchPath() {
        selectedPath.collect {
            it ?: return@collect
            if (it.isMdFile()) fetchContent(it)
            else _mdText.update { null }
        }
    }

    private suspend fun fetchContent(path: String) {
        _mdText.update { null }
        _mdText.update { getContent(path) }
    }

    private suspend fun fetchTree() {
        _tree.update { getTree() }
    }

    fun updatePath(path: String) = selectedPath.update { path }

    fun editTactic(tactic: Tactic) = scope.launch {
        modifyTactic(tactic, ContentAction.EDIT)
    }

    fun createTactic(tactic: Tactic) = scope.launch {
        modifyTactic(tactic, ContentAction.CREATE)
    }

    fun createCategory(path: String) = scope.launch {
        modifyCategory(path)
    }

    fun updateIsEditing(isEditing: Boolean) = _isEditingTactic.update { isEditing }
}