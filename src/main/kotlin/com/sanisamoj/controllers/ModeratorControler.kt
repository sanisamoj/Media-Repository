package com.sanisamoj.controllers

import com.sanisamoj.data.models.requests.AuthenticationRequest
import com.sanisamoj.services.ModeratorAuthenticateService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.moderatorRouting(){
    route("/moderator") {
        post("/login") {
            val authenticationRequest = call.receive<AuthenticationRequest>()

            try {
                val response = ModeratorAuthenticateService().login(authenticationRequest)
                return@post call.respond(response)

            } catch (e: Throwable) {
                return@post call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}