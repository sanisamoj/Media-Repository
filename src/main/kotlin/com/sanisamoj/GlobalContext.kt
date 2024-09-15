package com.sanisamoj

import com.sanisamoj.data.repository.LanguageResource
import com.sanisamoj.data.repository.MediaDefaultRepository
import java.io.File

object GlobalContext {
    private val currentProjectDir = System.getProperty("user.dir")
    const val MAX_FILE_SIZE: Int = 100 * 1024 * 1024 // 100MB
    const val MAX_HEADERS_SIZE: Int = 30 * 1024 * 1024 // 30MB
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif", "webp", "mkv", "mp4", "pdf")

    val publicImagesDir = File("$currentProjectDir/images/public")
    val privateImagesDir = File("$currentProjectDir/images/private")

    val systemMessages = LanguageResource()

    val imageRepository = MediaDefaultRepository()
}