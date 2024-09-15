package com.sanisamoj.data.repository

import com.sanisamoj.GlobalContext.MIME_TYPE_ALLOWED
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.utils.checkers.VerifyMimeType
import com.sanisamoj.utils.generators.CharactersGenerator
import io.ktor.http.content.*
import java.io.File

class MediaDefaultRepository: MediaRepository {
    override suspend fun saveMedia(multipartData: MultiPartData, path: File): List<String> {
        val imageNameList = saveAndReturnListNames(multipartData, path)
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
                    val mimeType = VerifyMimeType().returnType(part.originalFileName!!)

                    if (MIME_TYPE_ALLOWED.contains(mimeType) == false) {
                        imagePathOfSavedImages.forEach {
                            deleteMedia(it)
                        }

                        throw Exception(Errors.UnsupportedMediaType.description)
                    }

                    val fileBytes = part.streamProvider().readBytes()
                    val filename = "${CharactersGenerator().generateWithNoSymbols()}-${part.originalFileName}"
                    File(path, filename).writeBytes(fileBytes)
                    imageNameList.add(filename)

                    imagePathOfSavedImages.add(File(path, filename))
                }

                else -> {}
            }

            part.dispose()
        }

        return imageNameList
    }

    override fun getMedia(name: String, path: File): File {
        val file = File("$path/$name")
        return file
    }

    override fun deleteMedia(file: File) {
        file.delete()
    }
}