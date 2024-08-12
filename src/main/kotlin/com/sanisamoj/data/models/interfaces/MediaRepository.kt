package com.sanisamoj.data.models.interfaces

import io.ktor.http.content.*
import java.io.File

interface MediaRepository {
    suspend fun saveMedia(multipartData: MultiPartData): List<String>
    fun getMedia(name: String): File
    fun getAllMediaNames(): List<String>
    fun deleteMedia(file: File)

}