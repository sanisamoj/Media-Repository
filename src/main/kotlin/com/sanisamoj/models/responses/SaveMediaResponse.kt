package com.sanisamoj.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class SaveMediaResponse(
    val filename: String,
    val private: Boolean = false,
    val code: String? = null
)