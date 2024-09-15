package com.sanisamoj.routing

import com.sanisamoj.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.repository.LanguageResource
import com.sanisamoj.plugins.configureRouting
import com.sanisamoj.utils.converters.ObjectConverter
import com.sanisamoj.utils.generators.TokenGenerator
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageControllerKtTest {
    private val systemMessages = LanguageResource()
    private val IMAGE_NAME_TO_UPLOAD = "image.jpeg"
    private val languageResource = systemMessages

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

        val file = File(PUBLIC_IMAGES_DIR, IMAGE_NAME_TO_UPLOAD)

        val token: String = TokenGenerator().moderator()
        val response = client.post("/media") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
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

        deleteImageSaved(File(PUBLIC_IMAGES_DIR, imageSavedResponseInObject[0].filename))

        assertEquals(HttpStatusCode.OK, response.status, languageResource.testMessages.postPublicImageTest)
    }

    private fun deleteImageSaved(file: File) {
        file.delete()
        return
    }
}