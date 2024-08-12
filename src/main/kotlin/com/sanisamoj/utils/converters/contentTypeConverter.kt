package com.sanisamoj.utils.converters

import io.ktor.http.*

fun contentTypeConverter(extension: String) {
    val contentType = when (extension.toLowerCase()) {
        "jpg", "jpeg" -> ContentType.Image.JPEG
        "png" -> ContentType.Image.PNG
        "gif" -> ContentType.Image.GIF
        "webp" -> ContentType.Image.Any
        else -> ContentType.Application.OctetStream
    }
}