package com.sanisamoj.data.models.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class SaveMediaResponse(
    val filename: String,
    val private: Boolean = false,
    val code: String? = null
)