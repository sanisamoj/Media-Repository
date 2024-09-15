package com.sanisamoj.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResource(
    val testMessages: TestMessages = TestMessages()
)

@Serializable
data class TestMessages(
    val getImageTest: String = "Get image test",
    val postPublicImageTest: String = "Post public image test"
)
