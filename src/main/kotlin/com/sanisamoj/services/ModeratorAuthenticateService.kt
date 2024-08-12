package com.sanisamoj.services

import com.sanisamoj.data.models.requests.AuthenticationRequest
import com.sanisamoj.data.models.responses.AuthenticationResponse
import com.sanisamoj.utils.analyzers.dotEnv
import com.sanisamoj.utils.generators.TokenGenerator

class ModeratorAuthenticateService {
    fun login(authentication: AuthenticationRequest): AuthenticationResponse {
        val username = dotEnv("MODERATOR_LOGIN")
        val password = dotEnv("MODERATOR_PASSWORD")

        if(username != authentication.username || password != authentication.password) {
            throw Exception()
        }
        val token = TokenGenerator().moderator()
        return AuthenticationResponse(token)
    }
}