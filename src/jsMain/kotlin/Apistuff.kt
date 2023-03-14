import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

external fun getBaseUrl(): String
val baseUrl = getBaseUrl()

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getContent(): String =
    jsonClient.get("$baseUrl/test2").body()