package com.sanisamoj.data.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val username: String,
    val password: String
)