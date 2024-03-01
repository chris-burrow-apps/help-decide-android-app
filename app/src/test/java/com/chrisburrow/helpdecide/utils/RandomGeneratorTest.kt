package com.chrisburrow.helpdecide.utils

import junit.framework.TestCase.assertTrue
import org.junit.Test

class RandomGeneratorTest {

    @Test
    fun rangeTest() {

        val maxRange = 100

        val options = List(100) { RandomGenerator().generateNumber(maxRange) }

        for (option in options) {

            assertTrue(option <= maxRange)
        }
    }
}