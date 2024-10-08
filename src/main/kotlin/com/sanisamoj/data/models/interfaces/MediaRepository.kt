package com.sanisamoj.data.models.interfaces

import com.sanisamoj.data.models.dataclass.SavedMedia
import io.ktor.http.content.*
import java.io.File

interface MediaRepository {
    suspend fun saveMedia(multipartData: MultiPartData, path: File): List<String>
    suspend fun saveMediaInDb(filename: String, code: String): SavedMedia
    fun getMedia(name: String, path: File): File
    suspend fun getMediaInDb(name: String): SavedMedia?
    fun getAllPublicMediaNames(page: Int, size: Int): List<String>
    fun getAllPrivateMediaNames(page: Int, size: Int): List<String>
    fun countPublicMediaFiles(): Int
    fun countPrivateMediaFiles(): Int
    suspend fun deleteMedia(file: File, public: Boolean = true)
}