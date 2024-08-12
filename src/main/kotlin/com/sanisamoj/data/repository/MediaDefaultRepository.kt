package com.sanisamoj.data.repository

import com.sanisamoj.GlobalContext.MIME_TYPE_ALLOWED
import com.sanisamoj.GlobalContext.publicImagesDir
import com.sanisamoj.GlobalContext.systemMessages
import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.utils.checkers.VerifyMimeType
import com.sanisamoj.utils.generators.CharactersGenerator
import io.ktor.http.content.*
import java.io.File

class MediaDefaultRepository: MediaRepository {
    private val languageResource = systemMessages

    override suspend fun saveMedia(multipartData: MultiPartData): List<String> {
        val pathToPublicImages = publicImagesDir
        val imageNameList = saveAndReturnListNames(multipartData, pathToPublicImages)
        val imageSavedList: MutableList<String> = mutableListOf()
        imageNameList.forEach { name ->
            imageSavedList.add(name)
        }
        return imageSavedList
    }

    private suspend fun saveAndReturnListNames(multipartData: MultiPartData, path: File): List<String> {

        val imageNameList: MutableList<String> = mutableListOf()
        val imagePathOfSavedImages: MutableList<File> = mutableListOf()

        multipartData.forEachPart { part ->
            when (part) {

                is PartData.FileItem -> {

                    // Verifica o formato da imagem
                    val mimeType = VerifyMimeType().returnType(part.originalFileName!!)

                    if (MIME_TYPE_ALLOWED.contains(mimeType) == false) {
                        imagePathOfSavedImages.forEach {
                            deleteMedia(it)
                        }

                        throw Exception(languageResource.errorMessages.unsupportedMediaType)
                    }

                    // Salva os bytes da imagem
                    val fileBytes = part.streamProvider().readBytes()

                    // Gera um nome para a imagem
                    val filename = "${CharactersGenerator().generateWithNoSymbols()}-${part.originalFileName}"

                    // Salva a imagem
                    File(path, filename).writeBytes(fileBytes)

                    // Adiciona na array o nome da imagem salva
                    imageNameList.add(filename)

                    // Adiciona na array o caminho junto com o nome do arquivo
                    imagePathOfSavedImages.add(File(path, filename))
                }

                else -> {}
            }

            part.dispose()
        }

        return imageNameList
    }

    override fun getMedia(name: String): File {
        val publicImagesDir = publicImagesDir
        val file = File("$publicImagesDir/$name")

        return file
    }

    override fun getAllMediaNames(): List<String> {
        val publicImagesDir = publicImagesDir
        val files = publicImagesDir.listFiles()
        val mediaNames: MutableList<String> = mutableListOf()

        files?.forEach { file ->
            if (file.isFile) {
                mediaNames.add(file.name)
            }
        }

        return mediaNames
    }

    override fun deleteMedia(file: File) {
        file.delete()
    }
}