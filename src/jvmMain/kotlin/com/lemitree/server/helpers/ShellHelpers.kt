package com.lemitree.server.helpers

import java.io.File
import java.util.concurrent.TimeUnit

// File(baseDir)._runCommand("cat $path")
fun File._runCommand(
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
        .trimEnd()
}.onFailure { it.printStackTrace() }.getOrNull()

private fun Process.throwOnFail() = also {
    if (it.exitValue() == 1) error(it.errorReader().readText())
}

// File(baseDir).runBoolCommand<Boolean>("[[ -d $path ]] && echo true || echo false")
// File(baseDir).runBoolCommand<Int>("echo $?")
inline fun <reified T : Any> File.runCommand(
    cmd: String,
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
): T? {
    val output = _runCommand(cmd, timeoutAmount, timeoutUnit)
    return when (T::class) {
        String::class -> output as T?
        Int::class -> output?.toIntOrNull() as T?
        Boolean::class -> output.toBoolean() as T?
        else -> error("Unknown type or unable to parse: ${T::class.simpleName}")
    }
}

fun File.runListCommand(
    cmd: String,
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
): List<String> = _runCommand(cmd, timeoutAmount, timeoutUnit)
    ?.split("\n")
    ?: emptyList()