package com.sanisamoj.plugins

import com.sanisamoj.data.models.dataclass.ErrorResponse
import com.sanisamoj.data.pages.NotFoundPage
import com.sanisamoj.errors.errorResponse
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
            val response: Pair<HttpStatusCode, ErrorResponse> = errorResponse(cause.message)
            return@exception call.respond(response.first, message = response.second)
        }
    }
}