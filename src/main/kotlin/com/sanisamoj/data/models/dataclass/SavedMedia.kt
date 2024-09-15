package com.sanisamoj.data.models.dataclass

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class SavedMedia(
    @BsonId val id: ObjectId = ObjectId(),
    val filename: String,
    val private: Boolean = true,
    val code: String
)
