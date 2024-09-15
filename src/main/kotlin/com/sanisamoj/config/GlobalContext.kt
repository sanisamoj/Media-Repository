package com.sanisamoj.config

import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.data.models.interfaces.ServerContainer
import java.io.File

object GlobalContext {
    const val VERSION = "0.3.0"
    private val currentProjectDir = System.getProperty("user.dir")
    const val MAX_FILE_SIZE: Int = 100 * 1024 * 1024 // 100MB
    const val MAX_HEADERS_SIZE: Int = 30 * 1024 * 1024 // 30MB
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif", "webp", "mkv", "mp4", "pdf")

    val PUBLIC_IMAGES_DIR = File("$currentProjectDir/images/public")
    val PRIVATE_IMAGES_DIR = File("$currentProjectDir/images/private")

    private val serverContainer: ServerContainer = DefaultServerContainer()

    fun getMediaRepository(): MediaRepository = serverContainer.mediaRepository
}