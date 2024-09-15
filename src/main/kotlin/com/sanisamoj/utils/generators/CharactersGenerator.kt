package com.sanisamoj.utils.generators

class CharactersGenerator {
    fun generate(maxChat: Int = 36): String {
        val charPermited: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*?"
        val characters: String = (1..maxChat).map{ charPermited.random() }.joinToString("")
        return characters
    }

    fun generateWithNoSymbols(maxChat: Int = 36): String {
        val charPermited: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val characters: String = (1..maxChat).map{ charPermited.random() }.joinToString("")

        return characters

    }

}