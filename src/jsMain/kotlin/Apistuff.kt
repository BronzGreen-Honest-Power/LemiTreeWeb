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

suspend fun getContent(): String =
    jsonClient.get("$baseUrl/test2").body()