package com.sanisamoj.data.models.enums

enum class Errors(val description: String) {
    TotalImageUploadSizeExceeded("Total image upload size exceeded"),
    NoImageSaved("No images saved"),
    ImageSizeLimitExceeded("Image size limit exceeded"),
    InvalidLogin("Invalid email/password!"),
    UnsupportedMediaType("Unsupported media type"),
    InternalServerError("Internal server error!")
}