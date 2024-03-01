package com.chrisburrow.helpdecide.utils

class RandomStringGenerator {

    companion object {

        fun generateId() : String {

            val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return List(20) { alphabet.random() }.joinToString("")
        }
    }
}