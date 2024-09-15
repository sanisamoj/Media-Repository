package com.sanisamoj.services

import com.sanisamoj.config.GlobalContext
import com.sanisamoj.config.GlobalContext.PRIVATE_IMAGES_DIR
import com.sanisamoj.config.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.models.dataclass.SavedMedia
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.utils.generators.CharactersGenerator
import io.ktor.http.content.MultiPartData
import java.io.File

class MediaService(
    private val mediaRepository: MediaRepository = GlobalContext.getMediaRepository()
) {

    fun getMedia(imageName: String): File {
        val path: File = PUBLIC_IMAGES_DIR
        return mediaRepository.getMedia(imageName, path)
    }

    suspend fun getPrivateMedia(imageName: String, code: String): File {
        val path: File = PRIVATE_IMAGES_DIR
        val savedMedia: SavedMedia = mediaRepository.getMediaInDb(imageName)
            ?: throw Error(Errors.ImageNotFound.description)

        if(savedMedia.code != code) throw Error(Errors.TheImageHasAnotherOwner.description)

        return mediaRepository.getMedia(imageName, path)
    }

    suspend fun saveMedia(multipartData: MultiPartData, public: Boolean = true): List<SaveMediaResponse> {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        val listNames: List<String> = mediaRepository.saveMedia(multipartData, path)
        val saveMediaResponseList: MutableList<SaveMediaResponse> = mutableListOf()

        listNames.forEach { it ->
            if(!public) {
                val code: String = CharactersGenerator().generate(22)
                saveMediaResponseList.add(SaveMediaResponse(it, true, code))
                mediaRepository.saveMediaInDb(it, code)
            } else {
                saveMediaResponseList.add(SaveMediaResponse(it))
            }

        }
        return saveMediaResponseList
    }

    fun deleteMedia(imageName: String, public: Boolean = true) {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        val imageFile: File = mediaRepository.getMedia(imageName, path)
        mediaRepository.deleteMedia(imageFile)
    }

}