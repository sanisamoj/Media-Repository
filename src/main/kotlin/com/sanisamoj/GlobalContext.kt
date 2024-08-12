package com.sanisamoj

import com.sanisamoj.data.models.generic.LanguageResource
import com.sanisamoj.data.repository.MediaDefaultRepository
import com.sanisamoj.utils.converters.BytesConverter
import com.sanisamoj.utils.getCurrentJarDirectory
import java.io.File
import java.net.URLClassLoader

object GlobalContext {
    // Caminho para o projeto atual
    private val currentProjectDir = System.getProperty("user.dir")

    // Limite do tamanho das imagens que podem ser salvar no sistema
    const val MAX_FILE_SIZE: Int = 100 * 1024 * 1024 // 100MB

    // Limite do tamanho da requisição
    const val MAX_HEADERS_SIZE: Int = 30 * 1024 * 1024 // 30MB

    // Formatos de imagens permitidos
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif", "webp", "mkv", "mp4", "pdf")

    val currentDir = getCurrentJarDirectory()
    val publicImagesDir = File("$currentDir/images/public")
    val privateImagesDir = File("$currentDir/images/private")

    val systemMessages = LanguageResource()

    val imageRepository = MediaDefaultRepository()

    fun getMaxFileSizeInMb(): Double {
        val fileMaxSizeInLong = MAX_FILE_SIZE.toLong()
        return BytesConverter(fileMaxSizeInLong).getInMegabyte()
    }
}