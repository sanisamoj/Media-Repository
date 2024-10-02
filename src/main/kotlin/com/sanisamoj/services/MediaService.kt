package com.sanisamoj.services

import com.sanisamoj.config.GlobalContext
import com.sanisamoj.config.GlobalContext.PRIVATE_IMAGES_DIR
import com.sanisamoj.config.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.ResponseWithPagination
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.models.dataclass.SavedMedia
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.utils.generators.CharactersGenerator
import com.sanisamoj.utils.pagination.PaginationResponse
import com.sanisamoj.utils.pagination.paginationMethod
import io.ktor.http.content.MultiPartData
import java.io.File

class MediaService(
    private val mediaRepository: MediaRepository = GlobalContext.getMediaRepository()
) {

    fun getMedia(imageName: String): File {
        val path: File = PUBLIC_IMAGES_DIR
        return mediaRepository.getMedia(imageName, path)
    }

    fun getAllPublicMediaWithPagination(page: Int, size: Int): ResponseWithPagination<SaveMediaResponse> {
        val savedMediaNameList: List<String> = mediaRepository.getAllPublicMediaNames(page, size)
        val savedMediaResponseList: List<SaveMediaResponse> = savedMediaNameList.map { SaveMediaResponse(it) }
        val totalItems: Double = mediaRepository.countPublicMediaFiles().toDouble()
        val paginationResponse: PaginationResponse = paginationMethod(totalItems, size, page)
        return ResponseWithPagination<SaveMediaResponse>(savedMediaResponseList, paginationResponse)
    }

    suspend fun getAllPrivateMediaWithPagination(page: Int, size: Int): ResponseWithPagination<SaveMediaResponse> {
        val savedMediaNameList: List<String> = mediaRepository.getAllPrivateMediaNames(page, size)
        val savedMediaResponseList: List<SaveMediaResponse> = savedMediaNameList.map {
            val savedMedia: SavedMedia = mediaRepository.getMediaInDb(it)!!
            SaveMediaResponse(it, true,  savedMedia.code)
        }
        val totalItems: Double = mediaRepository.countPrivateMediaFiles().toDouble()
        val paginationResponse: PaginationResponse = paginationMethod(totalItems, size, page)
        return ResponseWithPagination<SaveMediaResponse>(savedMediaResponseList, paginationResponse)
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

    suspend fun deleteMedia(imageName: String, public: Boolean = true) {
        val path: File = if(public) PUBLIC_IMAGES_DIR else PRIVATE_IMAGES_DIR
        val imageFile: File = mediaRepository.getMedia(imageName, path)
        mediaRepository.deleteMedia(imageFile, public)
    }

}