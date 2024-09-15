package com.sanisamoj.config

import com.sanisamoj.data.models.interfaces.MediaRepository
import com.sanisamoj.data.models.interfaces.ServerContainer
import com.sanisamoj.data.repository.DefaultMediaRepository

class DefaultServerContainer: ServerContainer {
    override val mediaRepository: MediaRepository by lazy { DefaultMediaRepository() }
}