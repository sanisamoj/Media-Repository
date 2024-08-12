package com.sanisamoj.plugins

import com.sanisamoj.data.pages.NotFoundPage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.TooManyRequests) { call, status ->
            val retryAfter = call.response.headers["Retry-After"]
            call.respond(status, "Wait for $retryAfter seconds.")
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondText(
                text = buildString {
                    appendHTML().html {
                        NotFoundPage()
                    }
                },
                contentType = ContentType.Text.Html
            )
        }

        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}