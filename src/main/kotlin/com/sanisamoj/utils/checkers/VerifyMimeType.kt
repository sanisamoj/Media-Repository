package com.sanisamoj.utils.checkers

class VerifyMimeType {
    fun returnType(filename: String): String {
        val extension: String = filename.substringAfterLast('.', "")
        return extension
    }
}