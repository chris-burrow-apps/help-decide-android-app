package com.chrisburrow.helpdecide.utils

import junit.framework.TestCase
import org.junit.Test

class RandomStringGeneratorTest {

    @Test
    fun stringGeneration() {

        val expectedSize = 5

        val options = List(expectedSize) { RandomStringGenerator.generateId() }

        TestCase.assertTrue(options.distinct().size == expectedSize)
    }
}