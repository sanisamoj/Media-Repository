package com.sanisamoj.services

import com.sanisamoj.GlobalContext
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.models.interfaces.MediaRepository
import io.ktor.http.content.MultiPartData
import java.io.File

class MediaService(private val mediaRepository: MediaRepository = GlobalContext.imageRepository) {

    fun getImage(imageName: String): File {
        return mediaRepository.getMedia(imageName)
    }

    fun getAllMediaNames(): List<String> = mediaRepository.getAllMediaNames()

    suspend fun savePublicImage(multipartData: MultiPartData): List<SaveMediaResponse> {
        val listNames: List<String> = mediaRepository.saveMedia(multipartData)
        val saveMediaResponseList: MutableList<SaveMediaResponse> = mutableListOf()
        listNames.forEach { it ->
            saveMediaResponseList.add(SaveMediaResponse(it))
        }
        return saveMediaResponseList
    }

    fun deleteImage(imageName: String) {
        val imageFile: File = mediaRepository.getMedia(imageName)
        mediaRepository.deleteMedia(imageFile)
    }

}