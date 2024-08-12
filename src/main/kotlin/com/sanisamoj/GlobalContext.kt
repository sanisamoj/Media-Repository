package com.sanisamoj

<<<<<<< HEAD
import com.sanisamoj.data.models.generic.LanguageResource
import com.sanisamoj.data.repository.MediaDefaultRepository
import com.sanisamoj.utils.converters.BytesConverter
import com.sanisamoj.utils.getCurrentJarDirectory
import java.io.File
import java.net.URLClassLoader
=======
import com.sanisamoj.models.generic.LanguageResource
import com.sanisamoj.models.repository.MediaDefaultRepository
import com.sanisamoj.utils.converters.BytesConverter
import java.io.File
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c

object GlobalContext {
    // Caminho para o projeto atual
    private val currentProjectDir = System.getProperty("user.dir")

    // Limite do tamanho das imagens que podem ser salvar no sistema
<<<<<<< HEAD
    const val MAX_FILE_SIZE: Int = 100 * 1024 * 1024 // 100MB
=======
    const val MAX_FILE_SIZE: Int = 10 * 1024 * 1024 // 10MB
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c

    // Limite do tamanho da requisição
    const val MAX_HEADERS_SIZE: Int = 30 * 1024 * 1024 // 30MB

    // Formatos de imagens permitidos
<<<<<<< HEAD
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif", "webp", "mkv", "mp4", "pdf")

    val currentDir = getCurrentJarDirectory()
    val publicImagesDir = File("$currentDir/images/public")
    val privateImagesDir = File("$currentDir/images/private")
=======
    val MIME_TYPE_ALLOWED = listOf("jpeg", "png", "jpg", "gif")

    // Caminho para as imagens públicas
    val publicImagesDir = File("$currentProjectDir/images/public")

    // Caminho para as imagens privadas
    val privateImagesDir = File("$currentProjectDir/images/private")
>>>>>>> 1ba77c07e5db177862abccf3f2200b5d1760f28c

    val systemMessages = LanguageResource()

    val imageRepository = MediaDefaultRepository()

    fun getMaxFileSizeInMb(): Double {
        val fileMaxSizeInLong = MAX_FILE_SIZE.toLong()
        return BytesConverter(fileMaxSizeInLong).getInMegabyte()
    }
}