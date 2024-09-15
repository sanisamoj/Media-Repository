package com.sanisamoj.data.models.interfaces

import io.ktor.http.content.*
import java.io.File

interface MediaRepository {
    suspend fun saveMedia(multipartData: MultiPartData, path: File): List<String>
    fun getMedia(name: String, path: File): File
    fun deleteMedia(file: File)
}