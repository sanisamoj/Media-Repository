package com.sanisamoj.plugins

import com.sanisamoj.controllers.imageRouting
import com.sanisamoj.controllers.moderatorRouting
import com.sanisamoj.data.pages.HomePage
import com.sanisamoj.services.MediaService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

fun Application.configureRouting() {
    routing {
        get("/") {
            val mediaNamesList = MediaService().getAllMediaNames()

            return@get call.respondText(
                text = buildString {
                    appendHTML().html {
                        HomePage(mediaNamesList)
                    }
                },
                contentType = ContentType.Text.Html
            )
        }
        imageRouting()
        moderatorRouting()
    }
}
