package com.sanisamoj.plugins

import com.sanisamoj.routing.mediaRouting
import com.sanisamoj.routing.moderatorRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        mediaRouting()
        moderatorRouting()
    }
}
