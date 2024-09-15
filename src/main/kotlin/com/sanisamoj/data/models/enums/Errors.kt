package com.sanisamoj.data.models.enums

enum class Errors(val description: String) {
    TotalImageUploadSizeExceeded("Total image upload size exceeded!"),
    NoImageSaved("No images saved!"),
    ImageNotFound("Image not found!"),
    ImageSizeLimitExceeded("Image size limit exceeded!"),
    InvalidLogin("Invalid email/password!"),
    UnsupportedMediaType("Unsupported media type!"),
    InternalServerError("Internal server error!"),
    TheImageHasAnotherOwner("The image has another owner"),
    MediaNameNotProvided("Media name not provided!"),
    ImageCodeNotProvided("Image code not provided!")
}
