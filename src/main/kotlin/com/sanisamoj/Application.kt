package com.sanisamoj

import com.sanisamoj.plugins.*
import io.ktor.server.application.*

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
}
