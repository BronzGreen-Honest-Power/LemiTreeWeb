import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val scope: CoroutineScope,
) {
    private val _mdText = MutableStateFlow<String?>(null)
    val mdText: Flow<String?> = _mdText
    init {
        scope.launch {
            delay(500) // so that getBaseUrl is defined
            fetchContent()
        }
    }

    suspend fun fetchContent() {
        _mdText.update { getContent() }
    }
}