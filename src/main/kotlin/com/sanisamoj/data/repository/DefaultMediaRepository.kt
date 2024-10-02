package com.sanisamoj.data.repository

import com.sanisamoj.config.GlobalContext.MIME_TYPE_ALLOWED
import com.sanisamoj.config.GlobalContext.PRIVATE_IMAGES_DIR
import com.sanisamoj.config.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.SavedMedia
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.data.models.enums.Fields
import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.mongodb.CollectionsInDb
import com.sanisamoj.mongodb.MongodbOperations
import com.sanisamoj.mongodb.OperationField
import com.sanisamoj.utils.checkers.VerifyMimeType
import com.sanisamoj.utils.generators.CharactersGenerator
import io.ktor.http.content.*
import org.bson.types.ObjectId
import java.io.File

class DefaultMediaRepository : MediaRepository {
    override suspend fun saveMedia(multipartData: MultiPartData, path: File): List<String> {
        val imageNameList = saveAndReturnListNames(multipartData, path)
        val imageSavedList: MutableList<String> = mutableListOf()
        imageNameList.forEach { name ->
            imageSavedList.add(name)
        }
        return imageSavedList
    }

    override suspend fun saveMediaInDb(filename: String, code: String): SavedMedia {
        val savedMedia = SavedMedia(filename = filename, private = true, code = code)
        val saveMediaId: String = MongodbOperations().register(CollectionsInDb.Images, savedMedia).toString()
        return getMediaById(saveMediaId)
    }

    private suspend fun getMediaById(id: String): SavedMedia {
        return MongodbOperations().findOne<SavedMedia>(CollectionsInDb.Images, OperationField(Fields.Id, ObjectId(id)))
            ?: throw Error(Errors.ImageNotFound.description)
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

    override suspend fun getMediaInDb(name: String): SavedMedia {
        val savedMedia: SavedMedia = MongodbOperations().findOne<SavedMedia>(
            collectionName = CollectionsInDb.Images,
            filter = OperationField(Fields.Filename, name)
        ) ?: throw Error(Errors.ImageNotFound.description)

        return savedMedia
    }

    override fun getAllPublicMediaNames(page: Int, size: Int): List<String> {
        val publicImagesDir: File = PUBLIC_IMAGES_DIR
        val files = publicImagesDir.listFiles()?.filter { it.isFile }?.map { it.name }  ?: emptyList()

        val startIndex = (page - 1) * size
        val endIndex = minOf(startIndex + size, files.size)

        return if (startIndex < files.size) {
            files.subList(startIndex, endIndex)
        } else {
            emptyList()
        }

    }

    override fun getAllPrivateMediaNames(page: Int, size: Int): List<String> {
        val publicImagesDir: File = PRIVATE_IMAGES_DIR
        val files = publicImagesDir.listFiles()?.filter { it.isFile }?.map { it.name }  ?: emptyList()

        val startIndex = (page - 1) * size
        val endIndex = minOf(startIndex + size, files.size)

        return if (startIndex < files.size) {
            files.subList(startIndex, endIndex)
        } else {
            emptyList()
        }

    }

    override fun countPublicMediaFiles(): Int {
        val publicImagesDir: File = PUBLIC_IMAGES_DIR
        val files = publicImagesDir.listFiles()

        return files?.count { it.isFile } ?: 0
    }

    override fun countPrivateMediaFiles(): Int {
        val publicImagesDir: File = PRIVATE_IMAGES_DIR
        val files = publicImagesDir.listFiles()

        return files?.count { it.isFile } ?: 0
    }

    override suspend fun deleteMedia(file: File, public: Boolean) {
        file.delete()
        if(!public) {
            MongodbOperations().deleteItem<SavedMedia>(
                collectionName = CollectionsInDb.Images,
                filter = OperationField(Fields.Filename, file.name)
            )
        }
    }
}