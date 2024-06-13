package com.sanisamoj.services

import com.sanisamoj.GlobalContext
import com.sanisamoj.GlobalContext.systemMessages
import com.sanisamoj.models.interfaces.MediaRepository
import com.sanisamoj.models.responses.SaveMediaResponse
import io.ktor.http.content.*
import java.io.File

class MediaService(private val mediaRepository: MediaRepository = GlobalContext.imageRepository) {
    private val languageResource = systemMessages

    fun getImage(imageName: String): File {
        return mediaRepository.getMedia(imageName)
    }

    suspend fun savePublicImage(multipartData: MultiPartData): List<SaveMediaResponse> {
        val listNames = mediaRepository.saveMedia(multipartData)
        val saveMediaResponseList: MutableList<SaveMediaResponse> = mutableListOf()
        listNames.forEach { it ->
            saveMediaResponseList.add(SaveMediaResponse(it))
        }
        return saveMediaResponseList
    }

    suspend fun deleteImage(imageName: String) {
        val imageFile = mediaRepository.getMedia(imageName)
        mediaRepository.deleteMedia(imageFile)
    }

}