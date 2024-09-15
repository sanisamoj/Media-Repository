package com.sanisamoj.services

import com.sanisamoj.GlobalContext
import com.sanisamoj.GlobalContext.PRIVATE_IMAGES_DIR
import com.sanisamoj.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.models.interfaces.MediaRepository
import io.ktor.http.content.MultiPartData
import java.io.File

class MediaService(private val mediaRepository: MediaRepository = GlobalContext.imageRepository) {

    fun getMedia(imageName: String, public: Boolean = true): File {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        return mediaRepository.getMedia(imageName, path)
    }

    suspend fun saveMedia(multipartData: MultiPartData, public: Boolean = true): List<SaveMediaResponse> {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        val listNames: List<String> = mediaRepository.saveMedia(multipartData, path)
        val saveMediaResponseList: MutableList<SaveMediaResponse> = mutableListOf()
        listNames.forEach { it ->
            saveMediaResponseList.add(SaveMediaResponse(it))
        }
        return saveMediaResponseList
    }

    fun deleteMedia(imageName: String, public: Boolean = true) {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        val imageFile: File = mediaRepository.getMedia(imageName, path)
        mediaRepository.deleteMedia(imageFile)
    }

}