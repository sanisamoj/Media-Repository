package com.sanisamoj.models.generic

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val username: String,
    val password: String
)