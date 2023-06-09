package com.lemitree.web.data

import com.lemitree.common.data.Tactic
import com.lemitree.common.data.TreeItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

external fun getBaseUrl(): String
val baseUrl = getBaseUrl()

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

suspend fun getContent(path: String): String =
    jsonClient.get("$baseUrl/tactics/$path").body()

suspend fun getTree(): List<TreeItem> =
    jsonClient.get("$baseUrl/tree").body()

suspend fun createTactic(newTactic: Tactic): String =
    jsonClient.post("$baseUrl/tactic") {
        contentType(ContentType.Application.Json)
        setBody(newTactic)
    }.body()