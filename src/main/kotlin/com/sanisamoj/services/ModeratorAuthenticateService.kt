package com.sanisamoj.services

import com.sanisamoj.data.models.dataclass.AuthenticationRequest
import com.sanisamoj.data.models.dataclass.AuthenticationResponse
import com.sanisamoj.utils.analyzers.dotEnv
import com.sanisamoj.utils.generators.TokenGenerator

class ModeratorAuthenticateService {
    fun login(authentication: AuthenticationRequest): AuthenticationResponse {
        val username: String = dotEnv("MODERATOR_LOGIN")
        val password: String = dotEnv("MODERATOR_PASSWORD")

        if(username != authentication.username || password != authentication.password) {
            throw Exception()
        }
        val token: String = TokenGenerator().moderator()
        return AuthenticationResponse(token)
    }
}