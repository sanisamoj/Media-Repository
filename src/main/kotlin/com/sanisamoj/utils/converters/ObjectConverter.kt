package com.sanisamoj.utils.converters

import com.google.gson.Gson
import kotlinx.serialization.json.Json

class ObjectConverter {

    val gson = Gson()

    inline fun <reified T> stringToObject(objectInString: String): T {
        val result: T = gson.fromJson(objectInString, T::class.java)
        return result
    }

    inline fun <reified T> arrayStringToListObject(objectInString: String): List<T> {

        val result: List<T> = Json.decodeFromString(objectInString)
        return result

    }

}