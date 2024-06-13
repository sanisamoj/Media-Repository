package com.sanisamoj.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.cors() {
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHost("0.0.0.0")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
}