package com.sanisamoj.utils

import com.sanisamoj.config.GlobalContext
import java.nio.file.Path
import java.nio.file.Paths

fun getCurrentJarDirectory(): String {
    val path: Path = Paths.get(GlobalContext::class.java.protectionDomain.codeSource.location.toURI()).parent
    return path.toString()
}