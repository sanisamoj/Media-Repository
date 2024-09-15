package com.sanisamoj.routing

import com.sanisamoj.GlobalContext.MAX_HEADERS_SIZE
import com.sanisamoj.data.models.enums.Errors
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

    route("/media") {

        rateLimit {
            get("{media?}") {
                val imageName = call.parameters["media"] ?: return@get call.respondText("Nome da media não fornecido")
                val image: File = MediaService().getImage(imageName = imageName)
                if (image.exists()) return@get call.respondFile(image)
                else return@get call.respond(HttpStatusCode.NotFound)
            }

            authenticate("moderator-jwt") {
                post {
                    val multipartData = call.receiveMultipart()
                    val requestSize = call.request.headers.get(HttpHeaders.ContentLength)
                    val requestSizeInMb = BytesConverter(requestSize!!.toLong()).getInMegabyte()

                    val maxSizeInMb: Double = BytesConverter(MAX_HEADERS_SIZE.toLong()).getInMegabyte()
                    if (requestSizeInMb > maxSizeInMb) throw Exception(Errors.TotalImageUploadSizeExceeded.description)

                    val response = MediaService().savePublicImage(multipartData)

                    return@post call.respond(response)
                }
            }
        }
    }

}