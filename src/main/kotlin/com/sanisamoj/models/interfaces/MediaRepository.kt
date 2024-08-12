package com.sanisamoj.models.interfaces

import io.ktor.http.content.*
import java.io.File

interface MediaRepository {
    suspend fun saveMedia(multipartData: MultiPartData): List<String>
    fun getMedia(name: String): File
    fun deleteMedia(file: File)

}