package com.sanisamoj

import com.sanisamoj.controllers.ImageControllerKtTest
import com.sanisamoj.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        ImageControllerKtTest().runAllTest()
    }
}
