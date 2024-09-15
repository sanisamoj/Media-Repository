package com.sanisamoj.errors

import com.sanisamoj.config.GlobalContext.MAX_FILE_SIZE
import com.sanisamoj.config.GlobalContext.MIME_TYPE_ALLOWED
import com.sanisamoj.data.models.enums.DetailsMessages
import com.sanisamoj.data.models.enums.Errors
import com.sanisamoj.data.models.dataclass.ErrorResponse
import io.ktor.http.HttpStatusCode

fun errorResponse(errorMessage: String?): Pair<HttpStatusCode, ErrorResponse> {
    val response = when (errorMessage) {

        Errors.InvalidLogin.description -> {
            HttpStatusCode.Unauthorized to ErrorResponse(Errors.InvalidLogin.description)
        }

        Errors.UnsupportedMediaType.description -> {
            HttpStatusCode.UnsupportedMediaType to ErrorResponse(
                error = Errors.UnsupportedMediaType.description,
                details = "${DetailsMessages.AllowedImageTypes.message}: $MIME_TYPE_ALLOWED"
            )
        }

        Errors.ImageSizeLimitExceeded.description -> {
            HttpStatusCode.BadRequest to ErrorResponse(
                error = Errors.ImageSizeLimitExceeded.description,
                details = "${DetailsMessages.SaveAnImageWithSize.message} $MAX_FILE_SIZE MB"
            )
        }

        Errors.TotalImageUploadSizeExceeded.description -> {
            HttpStatusCode.BadRequest to ErrorResponse(
                error = Errors.TotalImageUploadSizeExceeded.description,
                details = "${DetailsMessages.AllowedSizeForTotalImages.message} $MAX_FILE_SIZE MB"
            )
        }

        Errors.NoImageSaved.description -> {
            HttpStatusCode.BadRequest to ErrorResponse(
                error = Errors.NoImageSaved.description
            )
        }

        Errors.MediaNameNotProvided.description -> {
            HttpStatusCode.BadRequest to ErrorResponse(
                error = Errors.MediaNameNotProvided.description
            )
        }

        Errors.ImageCodeNotProvided.description -> {
            HttpStatusCode.BadRequest to ErrorResponse(
                error = Errors.ImageCodeNotProvided.description
            )
        }

        Errors.TheImageHasAnotherOwner.description -> {
            HttpStatusCode.Forbidden to ErrorResponse(
                error = Errors.TheImageHasAnotherOwner.description
            )
        }

        else -> {
            HttpStatusCode.InternalServerError to ErrorResponse(
                error = Errors.InternalServerError.description
            )
        }
    }

    return response
}
