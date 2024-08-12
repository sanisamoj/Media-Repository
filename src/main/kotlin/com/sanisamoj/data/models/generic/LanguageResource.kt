package com.sanisamoj.data.models.generic

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResource(
    val errorMessages: ErrorMessages = ErrorMessages(),
    val errorDetails: ErrorMessagesDetails = ErrorMessagesDetails(),
    val testMessages: TestMessages = TestMessages()
)

@Serializable
data class ErrorMessages(
    val noImageSaved: String = "No images saved",
    val imageSizeLimitExceeded: String = "Image size limit exceeded",
    val unableToComplete: String = "Unable To Complete",
    val unsupportedMediaType: String = "Unsupported Media Type",
    val totalImageUploadSizeExceeded: String = "Total image upload size exceeded"
)

@Serializable
data class ErrorMessagesDetails(
    val saveAnImageWithSize: String = "Save an image with a size of up to",
    val allowedImageTypes: String = "The allowed types for images are",
    val allowedSizeForTotalImages: String = "The allowed size for total images are"
)

@Serializable
data class TestMessages(
    val getImageTest: String = "Get image test",
    val postPublicImageTest: String = "Post public image test"
)
