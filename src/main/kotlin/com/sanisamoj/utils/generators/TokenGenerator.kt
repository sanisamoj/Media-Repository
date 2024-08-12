package com.sanisamoj.utils.generators

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sanisamoj.utils.analyzers.dotEnv
import java.util.*
import java.util.concurrent.TimeUnit

class TokenGenerator {
    private val audience: String = dotEnv("JWT_AUDIENCE")
    private val domain: String = dotEnv("JWT_DOMAIN")

    fun moderator(): String {
        val secret: String = dotEnv("MODERATOR_JWT_SECRET")
        val currentTime: Long = System.currentTimeMillis()
        val tokenTimeInMillis: Long = TimeUnit.HOURS.toMillis(24)

        val token: String = JWT.create()
            .withAudience(audience)
            .withIssuer(domain)
            .withExpiresAt(Date(currentTime + tokenTimeInMillis))
            .sign(Algorithm.HMAC256(secret))

        return token
    }

}