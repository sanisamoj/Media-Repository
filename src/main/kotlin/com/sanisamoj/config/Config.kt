package com.sanisamoj.config

import com.sanisamoj.mongodb.MongoDatabase
import com.sanisamoj.utils.createImageDirectories

object Config {
    suspend fun initialize() {
        createImageDirectories()
        database()
        GlobalContext.getMediaRepository()
    }

    private suspend fun database() {
        MongoDatabase.initialize()
    }
}