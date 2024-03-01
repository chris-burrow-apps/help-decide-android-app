package com.chrisburrow.helpdecide.utils

class RandomGenerator : RandomNumberInterface {

    override fun generateNumber(max: Int): Int {

        return (0..max).random()
    }
}

interface RandomNumberInterface {

    fun generateNumber(max: Int) : Int
}