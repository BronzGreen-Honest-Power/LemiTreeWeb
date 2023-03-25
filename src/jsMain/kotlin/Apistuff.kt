import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

external fun getBaseUrl(): String
val baseUrl = getBaseUrl()

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getContent(path: String): String =
    jsonClient.get("$baseUrl/tactics/$path").body()

suspend fun getTree(): List<String> =
    jsonClient.get("$baseUrl/tree").body()