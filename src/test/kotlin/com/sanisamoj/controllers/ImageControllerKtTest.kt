package com.sanisamoj.controllers

import com.sanisamoj.GlobalContext.publicImagesDir
import com.sanisamoj.GlobalContext.systemMessages
import com.sanisamoj.models.responses.SaveMediaResponse
import com.sanisamoj.plugins.configureRouting
import com.sanisamoj.utils.converters.ObjectConverter
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageControllerKtTest {

    private val publicImageDir = publicImagesDir
    private val IMAGE_NAME_TO_UPLOAD = "image.jpeg"
    private val languageResource = systemMessages

    @Test
    fun runAllTest() {
        testGetImageName()
        testPostImage()
    }

    @Test
    fun testGetImageName() = testApplication {
        val response = client.get("/media?media=image.jpeg")
        assertEquals(HttpStatusCode.OK, response.status, languageResource.testMessages.getImageTest)
    }

    @Test
    fun testPostImage() = testApplication {
        application {
            configureRouting()
        }

        val file = File(publicImageDir, IMAGE_NAME_TO_UPLOAD)

        val response = client.post("/media") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("private", "no")
                        append("image", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.jpeg\"")
                        })
                    }
                )
            )
        }

        val imageSavedResponseInString = response.bodyAsText()

        val imageSavedResponseInObject = ObjectConverter()
            .arrayStringToListObject<SaveMediaResponse>(imageSavedResponseInString)

        deleteImageSaved(File(publicImageDir, imageSavedResponseInObject[0].filename))

        assertEquals(HttpStatusCode.OK, response.status, languageResource.testMessages.postPublicImageTest)
    }

    private fun deleteImageSaved(file: File) {
        file.delete()
        return
    }
}