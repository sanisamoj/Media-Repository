package com.sanisamoj.utils.generators

import kotlin.random.Random

class CharactersGenerator {

    // Gera um conjunto de caracteres
    fun generate(maxChat: Int = 36): String {

        // Caracteres permitidos
        val charPermited: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*?"

        // Irá gerar um conjunto de caracteres
        val characters: String = (1..maxChat).map{ charPermited.random() }.joinToString("")

        return characters

    }

    // Gera um conjunto de caracteres , com caracteres aceitos como nomes
    fun generateWithNoSymbols(maxChat: Int = 36): String {

        // Caracteres permitidos
        val charPermited: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

        // Irá gerar um conjunto de caracteres
        val characters: String = (1..maxChat).map{ charPermited.random() }.joinToString("")

        return characters

    }

}