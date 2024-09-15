package com.sanisamoj.routing

import com.sanisamoj.GlobalContext.MAX_HEADERS_SIZE
import com.sanisamoj.data.models.dataclass.SaveMediaResponse
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.services.MediaService
import com.sanisamoj.utils.converters.BytesConverter
import io.ktor.http.*
import io.ktor.http.content.MultiPartData
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
                val imageName: String = call.parameters["media"] ?: return@get call.respondText("Nome da media não fornecido")
                val image: File = MediaService().getMedia(imageName = imageName)
                if (image.exists()) return@get call.respondFile(image)
                else return@get call.respond(HttpStatusCode.NotFound)
            }

            authenticate("moderator-jwt") {

                post {
                    val multipartData: MultiPartData = call.receiveMultipart()
                    val requestSize: String? = call.request.headers[HttpHeaders.ContentLength]
                    val requestSizeInMb: Double = BytesConverter(requestSize!!.toLong()).getInMegabyte()

                    val maxSizeInMb: Double = BytesConverter(MAX_HEADERS_SIZE.toLong()).getInMegabyte()
                    if (requestSizeInMb > maxSizeInMb) throw Exception(Errors.TotalImageUploadSizeExceeded.description)

                    val imageName: String? = call.parameters["private"]
                    val private: Boolean = imageName == "true"

                    val response: List<SaveMediaResponse> = MediaService().saveMedia(multipartData, !private)

                    return@post call.respond(response)
                }
            }
        }
    }

}