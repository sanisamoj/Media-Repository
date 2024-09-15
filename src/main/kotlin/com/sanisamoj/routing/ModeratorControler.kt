package com.sanisamoj.routing

import com.sanisamoj.data.models.dataclass.AuthenticationRequest
import com.sanisamoj.data.models.dataclass.AuthenticationResponse
import com.sanisamoj.services.ModeratorAuthenticateService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.moderatorRouting(){
    route("/moderator") {
        post("/login") {
            val authenticationRequest: AuthenticationRequest = call.receive<AuthenticationRequest>()
            val response: AuthenticationResponse = ModeratorAuthenticateService().login(authenticationRequest)
            return@post call.respond(response)
        }
    }
}