package com.sanisamoj

import com.sanisamoj.plugins.*
import com.sanisamoj.utils.createImageDirectories
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureRateLimit()
    configureStatusPage()
    configureSerialization()
    configureRouting()
    cors()
    startBackgroundTasks()
}

private fun startBackgroundTasks() {
    CoroutineScope(Dispatchers.Default).launch {
        createImageDirectories()
    }

}
