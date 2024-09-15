package com.sanisamoj.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sanisamoj.utils.analyzers.dotEnv
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthentication() {
    val moderatorSecret: String = dotEnv("MODERATOR_JWT_SECRET")
    val audience: String = dotEnv("JWT_AUDIENCE")
    val domain: String = dotEnv("JWT_DOMAIN")

    authentication {
        jwt("moderator-jwt") {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(moderatorSecret))
                    .withAudience(audience)
                    .withIssuer(domain)
                    .build()
            )

            validate { credential ->
                if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

}