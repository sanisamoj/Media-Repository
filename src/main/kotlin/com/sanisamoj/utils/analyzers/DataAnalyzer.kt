package com.sanisamoj.utils.analyzers

const val MAX_CHARACTERS_ALLOWED = 100

class DataAnalyzer {
    fun isValidDocument(doc: String): Boolean {
        if(doc.length != 11 || doc.any { !it.isDigit() }) return false

        val docNumbers = doc.substring(0, 9).map { it.toString().toInt() }.toIntArray()
        val dv1 = calculateVerificationDigit(docNumbers)
        val dv2 = calculateVerificationDigit(docNumbers + dv1)

        return doc.substring(9, 11) == "$dv1$dv2"
    }

    private fun calculateVerificationDigit(docNumbers: IntArray) : Int {
        var weight = docNumbers.size + 1
        val sum = docNumbers.fold(0) { acc, number ->
            acc + number * weight--
        }

        val remainder = sum % 11
        return if(remainder < 2) 0 else 11 - remainder
    }

    fun isMaxCharactersExceeded(characters: List<String>): Boolean {
        characters.map {
            if(it.length > MAX_CHARACTERS_ALLOWED) return true
        }

        return false
    }
}