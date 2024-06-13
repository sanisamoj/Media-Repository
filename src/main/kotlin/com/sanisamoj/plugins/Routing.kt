package com.sanisamoj.plugins

import com.sanisamoj.controllers.imageRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        imageRouting()
    }
}
