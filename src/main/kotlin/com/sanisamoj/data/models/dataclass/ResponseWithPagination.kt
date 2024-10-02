package com.sanisamoj.data.models.dataclass

import com.sanisamoj.utils.pagination.PaginationResponse
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithPagination<T>(
    val content: List<T>,
    val paginationResponse: PaginationResponse
)
