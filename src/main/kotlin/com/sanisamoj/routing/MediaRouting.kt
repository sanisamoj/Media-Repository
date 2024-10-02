package com.sanisamoj.routing

import com.sanisamoj.config.GlobalContext.MAX_HEADERS_SIZE
import com.sanisamoj.data.models.dataclass.ResponseWithPagination
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

fun Route.mediaRouting() {

    route("/media") {

        rateLimit {
            
            get {
                val imageName: String = call.request.queryParameters["media"] ?: throw Error(Errors.MediaNameNotProvided.description)
                val image: File = MediaService().getMedia(imageName = imageName)
                if (image.exists()) return@get call.respondFile(image)
                else return@get call.respond(HttpStatusCode.NotFound)
            }

            get("/private") {
                val imageName: String = call.request.queryParameters["media"] ?: throw Error(Errors.MediaNameNotProvided.description)
                val code: String = call.request.queryParameters["code"] ?: throw Error(Errors.ImageCodeNotProvided.description)
                val image: File = MediaService().getPrivateMedia(imageName = imageName, code = code)
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

                get {
                    val page: String? = call.request.queryParameters["page"]
                    val size: String? = call.request.queryParameters["size"]
                    val private: Boolean = call.request.queryParameters["private"] == "true"

                    val pageNumber: Int? = page?.toIntOrNull()
                    val pageSize: Int? = size?.toIntOrNull()

                    if (pageNumber != null && pageSize != null) {
                        if(private) {
                            val responseWithPagination: ResponseWithPagination<SaveMediaResponse> =
                                MediaService().getAllPrivateMediaWithPagination(pageNumber, pageSize)
                            return@get call.respond(responseWithPagination)
                        } else {
                            val responseWithPagination: ResponseWithPagination<SaveMediaResponse> =
                                MediaService().getAllPublicMediaWithPagination(pageNumber, pageSize)
                            return@get call.respond(responseWithPagination)
                        }
                    } else {
                        throw Error(Errors.InvalidPageOrSizeParameters.description)
                    }
                }

                delete {
                    val name: String = call.request.queryParameters["name"].toString()
                    val private: Boolean = call.request.queryParameters["private"] == "true"
                    MediaService().deleteMedia(name, !private)
                    return@delete call.respond(HttpStatusCode.OK)
                }
            }
        }
    }

}