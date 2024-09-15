package com.sanisamoj.plugins

import com.sanisamoj.data.models.dataclass.ErrorResponse
import com.sanisamoj.errors.errorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.TooManyRequests) { call, status ->
            val retryAfter = call.response.headers["Retry-After"]
            call.respond(status, "Wait for $retryAfter seconds.")
        }

        exception<Throwable> { call, cause ->
            val response: Pair<HttpStatusCode, ErrorResponse> = errorResponse(cause.message)
            return@exception call.respond(response.first, message = response.second)
        }
    }
}