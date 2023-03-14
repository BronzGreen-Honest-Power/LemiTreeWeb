import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import java.io.File
import java.util.concurrent.TimeUnit

val mod = module {
    single { Sesame("quackz") }
}
data class Sesame(val open: String)

fun main() {
    startKoin {
        modules(mod)
    }
    val baseDir = System.getenv("BASE_DIR_LEMITREE") ?: error("BASE_DIR_LEMITREE env not found!")

    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
//            route(ShoppingListItem.path) {
//                get {
//                    call.respond(collection.find().toList())
//                }
//                post {
//                    collection.insertOne(call.receive<ShoppingListItem>())
//                    call.respond(HttpStatusCode.OK)
//                }
//                delete("/{id}") {
//                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//                    collection.deleteOne(ShoppingListItem::id eq id)
//                    call.respond(HttpStatusCode.OK)
//                }
//            }
            val x: Sesame by inject()
            println("AAAAAAAAAAA: ${x.open}")
            route("/test") {
                get {
                    coroutineScope {
                        launch(Dispatchers.IO) {
                            val test = File("$baseDir/Human_Individual")
                                .runCommand("du -a | grep -v git")
                            println(test)
                            call.respond(test ?: "Error")
                        }
                    }
                }
            }
            route("/test2") {
                get {
                    coroutineScope {
                        launch(Dispatchers.IO) {
                            val test = File("$baseDir/Human_Individual")
                                .runCommand("cat Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Don\\'ts/Smoking_cigarettes.md")
                            println(test)
                            call.respond(test ?: "Error")
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}

fun File.runCommand(
    cmd: String,
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = runCatching {
    ProcessBuilder("/bin/bash", "-c", cmd)
        .directory(this)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
//        .redirectErrorStream(true)
        .start().also { it.waitFor(timeoutAmount, timeoutUnit) }
        .inputStream.bufferedReader().readText()
}.onFailure { it.printStackTrace() }.getOrNull()
//    val errorStream = proc.errorStream.bufferedReader().readText()