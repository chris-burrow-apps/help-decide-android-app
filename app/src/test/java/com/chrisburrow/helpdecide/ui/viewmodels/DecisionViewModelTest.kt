package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomNumberInterface
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class DecisionViewModelTest {

    @Test
    fun generateCalled() {

        val options = listOf(OptionObject(text = "Option 1"))

        val randomGenerator = MockRandomGenerator()

        val decisionViewModel = DecisionViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            randomGenerator = randomGenerator,
            options = options,
        )

        decisionViewModel.chooseOption()

        assertTrue(randomGenerator.generateCalled)
        assertEquals(options.first(), decisionViewModel.decidedOption)
    }

    @Test
    fun rangeZeroGiven_whenOneOption() {

        val options = List(1) { OptionObject(text = "$it") }

        val randomGenerator = MockRandomGenerator()
        val decisionViewModel = DecisionViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            randomGenerator = randomGenerator,
            options = options,
        )

        decisionViewModel.chooseOption()

        assertEquals(0, randomGenerator.rangeGiven)
    }

    @Test
    fun rangeGiven() {

        val randomLength = (1..50).random()

        val options = List(randomLength) { OptionObject(text = "$it") }

        val randomGenerator = MockRandomGenerator()
        val decisionViewModel = DecisionViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            randomGenerator = randomGenerator,
            options = options,
        )

        decisionViewModel.chooseOption()

        assertEquals(randomLength - 1, randomGenerator.rangeGiven)
    }
}

class MockRandomGenerator : RandomNumberInterface {

    var generateCalled: Boolean = false
    var rangeGiven: Int = -1

    override fun generateNumber(max: Int): Int {
        generateCalled = true
        rangeGiven = max

        return 0
    }
}