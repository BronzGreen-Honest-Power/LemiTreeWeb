import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.ext.inject

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
                call.respond(HttpStatusCode.BadRequest)
            }
            val x: Sesame by inject()
            println("AAAAAAAAAAA: ${x.open}")
            get("/tree") {
                coroutineScope {
                    launch(Dispatchers.IO) {
                        val test = File(baseDir)
                            .runCommand("du -a | grep -v \\.git | awk -F '\\t' '{print $2}'")
                        test?.split("\n")?.forEach { println("quack") }
                        call.respond(test ?: "Error")
                    }
                }
            }
            get("/tactics/{path...}") {
                val path = call.parameters.getAll("path")?.joinToString("/")
                if (path.isNullOrEmpty()) call.respond("Error: Invalid path")
                println("path: $path")
                // todo: secure against tracing path to other files outside of the repo
                coroutineScope {
                    launch(Dispatchers.IO) {
                        val file = File("$baseDir/$path")
                        val response = when {
                            !file.exists() -> "Error: File not found"
                            file.isDirectory -> "Error: This is a directory"
                            else -> file.bufferedReader().readText()
                        }
                        call.respond(response)
                    }
                }
            }
        }
    }.start(wait = true)
}

// File(baseDir).runCommand("cat $path")
fun File.runCommand(
    cmd: String,
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
): String? = runCatching {
    println("Running: $cmd")
    ProcessBuilder("/bin/bash", "-c", cmd)
        .directory(this)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
//        .redirectErrorStream(true)
        .start()
        .also { it.waitFor(timeoutAmount, timeoutUnit) }
        .throwOnFail()
        .inputStream
        .bufferedReader()
        .readText()
}.onFailure { it.printStackTrace() }.getOrNull()

fun Process.throwOnFail() = also {
    if (it.exitValue() == 1) error(it.errorReader().readText())
}

// File(baseDir).runBoolCommand("[[ -d $path ]] && echo true || echo false")
fun File.runBoolCommand(
    cmd: String,
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
) = runCommand(cmd, timeoutAmount, timeoutUnit)
    ?.trimEnd()
    ?.toBoolean() ?: false