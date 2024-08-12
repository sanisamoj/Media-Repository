package com.sanisamoj

import com.sanisamoj.plugins.*
<<<<<<< HEAD
import com.sanisamoj.utils.createImageDirectories
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
=======
import io.ktor.server.application.*
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c

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
<<<<<<< HEAD
    startBackgroundTasks()
}

private fun startBackgroundTasks() {
    CoroutineScope(Dispatchers.Default).launch {
        createImageDirectories()
    }
=======
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c
}
