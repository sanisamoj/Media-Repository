package com.sanisamoj.utils.checkers

class VerifyMimeType {
    fun returnType(filename: String): String {
        val extension = filename.substringAfterLast('.', "")
        return extension
    }
}