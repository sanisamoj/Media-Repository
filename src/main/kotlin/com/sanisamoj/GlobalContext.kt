package com.sanisamoj

import com.sanisamoj.models.generic.LanguageResource
import com.sanisamoj.models.repository.MediaDefaultRepository
import com.sanisamoj.utils.converters.BytesConverter
import java.io.File

object GlobalContext {
    // Caminho para o projeto atual
    private val currentProjectDir = System.getProperty("user.dir")

    // Limite do tamanho das imagens que podem ser salvar no sistema
    const val MAX_FILE_SIZE: Int = 10 * 1024 * 1024 // 10MB

    // Limite do tamanho da requisição
    const val MAX_HEADERS_SIZE: Int = 30 * 1024 * 1024 // 30MB

    // Formatos de imagens permitidos
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif")

    // Caminho para as imagens públicas
    val publicImagesDir = File("$currentProjectDir/images/public")

    // Caminho para as imagens privadas
    val privateImagesDir = File("$currentProjectDir/images/private")

    val systemMessages = LanguageResource()

    val imageRepository = MediaDefaultRepository()

    fun getMaxFileSizeInMb(): Double {
        val fileMaxSizeInLong = MAX_FILE_SIZE.toLong()
        return BytesConverter(fileMaxSizeInLong).getInMegabyte()
    }
}