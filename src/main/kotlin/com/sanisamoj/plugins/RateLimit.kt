package com.sanisamoj.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ratelimit.RateLimit
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {
        register {
            rateLimiter(limit = 3, refillPeriod = 1.seconds)
        }
    }
}