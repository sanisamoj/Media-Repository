package com.sanisamoj.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ratelimit.RateLimit

fun Application.configureRateLimit() {
    install(RateLimit) {

    }
}