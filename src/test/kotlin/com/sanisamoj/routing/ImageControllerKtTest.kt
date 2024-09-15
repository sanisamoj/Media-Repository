package com.sanisamoj.routing

import com.sanisamoj.config.GlobalContext.PRIVATE_IMAGES_DIR
import com.sanisamoj.config.GlobalContext.PUBLIC_IMAGES_DIR
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
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
    private val imageNameTest = "image.jpeg"

    @Test
    fun testGetImageName() = testApplication {
        val response: HttpResponse = client.get("/media?media=image.jpeg")
        assertEquals(HttpStatusCode.OK, response.status)

        val responseNotFound: HttpResponse = client.get("/media?media=image2.jpeg")
        assertEquals(HttpStatusCode.NotFound, responseNotFound.status)
    }

    @Test
    fun testPostImage() = testApplication {
        application {
            configureRouting()
        }

        val file = File(PUBLIC_IMAGES_DIR, imageNameTest)

        val token: String = TokenGenerator().moderator()
        val response: HttpResponse = client.post("/media") {
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

        val imageSavedResponseInString: String = response.bodyAsText()

        val imageSavedResponseInObject: List<SaveMediaResponse> = ObjectConverter()
            .arrayStringToListObject<SaveMediaResponse>(imageSavedResponseInString)

        deleteImageSaved(File(PUBLIC_IMAGES_DIR, imageSavedResponseInObject[0].filename))

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testPostAndGetPrivateImage() = testApplication {
        application {
            configureRouting()
        }

        val file = File(PUBLIC_IMAGES_DIR, imageNameTest)

        val token: String = TokenGenerator().moderator()
        val postResponse: HttpResponse = client.post("/media") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            url {
                parameters.append("private", "true")  // Private como query param
            }
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("image", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.jpeg\"")
                        })
                    }
                )
            )
        }

        val postResponseBodyInString: String = postResponse.bodyAsText()
        val savedMediaResponse: List<SaveMediaResponse> = ObjectConverter()
            .arrayStringToListObject<SaveMediaResponse>(postResponseBodyInString)

        val savedImage = savedMediaResponse[0]
        assertEquals(HttpStatusCode.OK, postResponse.status)

        val correctCodeResponse: HttpResponse = client.get("/media/private") {
            url {
                parameters.append("media", savedImage.filename)
                parameters.append("code", savedImage.code ?: "")
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        assertEquals(HttpStatusCode.OK, correctCodeResponse.status)

        val incorrectCodeResponse: HttpResponse = client.get("/media/private") {
            url {
                parameters.append("media", savedImage.filename)
                parameters.append("code", "wrongCode123")
            }
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        assertEquals(HttpStatusCode.Forbidden, incorrectCodeResponse.status)

        deleteImageSaved(File(PRIVATE_IMAGES_DIR, savedImage.filename))
    }

    private fun deleteImageSaved(file: File) {
        file.delete()
    }
}