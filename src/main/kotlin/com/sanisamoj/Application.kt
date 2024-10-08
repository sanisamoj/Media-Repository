package com.sanisamoj

import com.sanisamoj.config.Config
import com.sanisamoj.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureStatusPage()
    configureSerialization()
    configureRouting()
    configureHTTP()
    startBackgroundTasks()
}

private fun startBackgroundTasks() {
    CoroutineScope(Dispatchers.Default).launch {
        Config.initialize()
    }

}
