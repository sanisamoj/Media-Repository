package com.sanisamoj.plugins

<<<<<<< HEAD
import com.sanisamoj.data.pages.NotFoundPage
=======
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
<<<<<<< HEAD
import kotlinx.html.html
import kotlinx.html.stream.appendHTML
=======
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.TooManyRequests) { call, status ->
            val retryAfter = call.response.headers["Retry-After"]
            call.respond(status, "Wait for $retryAfter seconds.")
        }

<<<<<<< HEAD
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

=======
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}