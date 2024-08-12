package com.sanisamoj.controllers

import com.sanisamoj.GlobalContext.MAX_HEADERS_SIZE
import com.sanisamoj.GlobalContext.MIME_TYPE_ALLOWED
import com.sanisamoj.GlobalContext.systemMessages
import com.sanisamoj.data.models.responses.ErrorResponse
import com.sanisamoj.services.MediaService
import com.sanisamoj.utils.converters.BytesConverter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.imageRouting() {
    val languageResource = systemMessages
    val errorMessages = languageResource.errorMessages
    val errorDetails = languageResource.errorDetails
    val MAX_HEADERS_SIZE = BytesConverter(MAX_HEADERS_SIZE.toLong()).getInMegabyte()

    route("/media") {

        rateLimit {
            // Responsável por retornar uma imagem
            get("{media?}") {
                val imageName = call.parameters["media"] ?: return@get call.respondText("Nome da media não fornecido")
                val image: File = MediaService().getImage(imageName = imageName)
                if(image.exists()) return@get call.respondFile(image)

                else return@get call.respond(HttpStatusCode.NotFound)
            }

            authenticate("moderator-jwt") {
                // Rota responsável por salvar uma imagem
                post{
                    val multipartData = call.receiveMultipart()
                    val requestSize = call.request.headers.get(HttpHeaders.ContentLength)
                    val requestSizeInMb = BytesConverter(requestSize!!.toLong()).getInMegabyte()

                    try {
                        // Caso o tamanho da requisição ultrapasse o tamanho limite
                        if(requestSizeInMb > MAX_HEADERS_SIZE) throw Exception(errorMessages.totalImageUploadSizeExceeded)

                        // Tenta salvar a imagem
                        val response = MediaService().savePublicImage(multipartData)

                        return@post call.respond(response)

                    } catch (e: Throwable) {

                        if(e.message == errorMessages.totalImageUploadSizeExceeded)
                            return@post call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = ErrorResponse(
                                    error = errorMessages.totalImageUploadSizeExceeded,
                                    details = "${errorDetails.allowedSizeForTotalImages} ${MAX_HEADERS_SIZE.toInt()}MB"
                                )
                            )

                        if(e.message == errorMessages.unsupportedMediaType)
                            return@post call.respond(
                                status = HttpStatusCode.BadRequest,
                                message = ErrorResponse(
                                    error = errorMessages.unsupportedMediaType,
                                    details = "${errorDetails.allowedImageTypes}: $MIME_TYPE_ALLOWED"
                                )
                            )

                        return@post call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}