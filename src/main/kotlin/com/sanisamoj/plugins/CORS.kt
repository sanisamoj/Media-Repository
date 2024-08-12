package com.sanisamoj.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.cors() {
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
<<<<<<< HEAD
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Delete)
=======
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c
        allowHost("0.0.0.0")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
}