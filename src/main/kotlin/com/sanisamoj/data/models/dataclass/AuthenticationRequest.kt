package com.sanisamoj.data.models.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val username: String,
    val password: String
)