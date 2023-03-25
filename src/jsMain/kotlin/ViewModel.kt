import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val scope: CoroutineScope,
) {
    private val _tree = MutableStateFlow<List<String>>(emptyList())
    private val _path = MutableStateFlow<String>("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Caffeine_before_bed.md")
    private val _mdText = MutableStateFlow<String?>(null)
    val mdText: Flow<String?> = _mdText
    init {
        scope.launch {
            delay(500) // so that getBaseUrl is defined
            fetchContent()
            fetchTree()
        }
    }

    suspend fun fetchContent() {
        _mdText.update { null }
        _mdText.update { getContent(_path.value) }
    }

    suspend fun fetchTree() {
        _tree.update { getTree() }
    }
}