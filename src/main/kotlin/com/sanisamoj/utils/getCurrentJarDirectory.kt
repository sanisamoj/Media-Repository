package com.sanisamoj.utils

import com.sanisamoj.GlobalContext
import java.nio.file.Paths

fun getCurrentJarDirectory(): String {
    val path = Paths.get(GlobalContext::class.java.protectionDomain.codeSource.location.toURI()).parent
    return path.toString()
}