package com.sanisamoj.utils

import java.io.File

fun createImageDirectories() {
    val currentDir = getCurrentJarDirectory()
    val publicImagesDir = File("$currentDir/images/public")
    val privateImagesDir = File("$currentDir/images/private")

    if (!publicImagesDir.exists()) {
        publicImagesDir.mkdirs()
    }

    if (!privateImagesDir.exists()) {
        privateImagesDir.mkdirs()
    }
}