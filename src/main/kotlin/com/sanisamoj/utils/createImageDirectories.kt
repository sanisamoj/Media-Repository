package com.sanisamoj.utils

import java.io.File

fun createImageDirectories() {
    val currentDir: String = getCurrentJarDirectory()
    val publicImagesDir: File = File("$currentDir/images/public")
    val privateImagesDir: File = File("$currentDir/images/private")

    if (!publicImagesDir.exists()) {
        publicImagesDir.mkdirs()
    }

    if (!privateImagesDir.exists()) {
        privateImagesDir.mkdirs()
    }
}