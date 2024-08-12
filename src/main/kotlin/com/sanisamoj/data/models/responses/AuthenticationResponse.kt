package com.sanisamoj.data.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val token: String
)
