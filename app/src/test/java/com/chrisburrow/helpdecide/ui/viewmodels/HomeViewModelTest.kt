package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun defaultState() {

        val homeViewModel = HomeViewModel(analyticsLibrary = MockAnalyticsLibrary())

        assertFalse(homeViewModel.view.decideOption)
        assertFalse(homeViewModel.view.clearAllShown)
        assertTrue(homeViewModel.view.emptyView)

        assertEquals(0, homeViewModel.view.options.size)
    }

    @Test
    fun addOption() {

        val homeViewModel = HomeViewModel(analyticsLibrary = MockAnalyticsLibrary())

        val newOption = OptionObject(text = "Option 1")

        homeViewModel.addOption(newOption)

        assertTrue(homeViewModel.view.options.contains(newOption))
        assertFalse(homeViewModel.view.decideOption)
        assertFalse(homeViewModel.view.emptyView)
        assertTrue(homeViewModel.view.clearAllShown)

        val anotherOption = OptionObject(text = "Option 2")

        homeViewModel.addOption(anotherOption)

        assertTrue(homeViewModel.view.options.contains(anotherOption))
        assertTrue(homeViewModel.view.decideOption)
        assertFalse(homeViewModel.view.emptyView)
        assertTrue(homeViewModel.view.clearAllShown)
    }

    @Test
    fun deleteOption() {

        val options = List(5) { OptionObject(text = "$it") }

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            initialOptions = options
        )

        assertEquals(options, homeViewModel.view.options)

        val deleteOption = options[0]

        homeViewModel.deleteOption(deleteOption)

        assertFalse(homeViewModel.view.options.contains(deleteOption))
        assertTrue(homeViewModel.view.decideOption)
        assertFalse(homeViewModel.view.emptyView)
    }

    @Test
    fun clearOptions() {

        val options = List(5) { OptionObject(text = "$it") }

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            initialOptions = options
        )

        assertEquals(options, homeViewModel.view.options)

        homeViewModel.clearOptions()

        assertEquals(0, homeViewModel.view.options.size)
        assertFalse(homeViewModel.view.decideOption)
        assertTrue(homeViewModel.view.emptyView)
        assertFalse(homeViewModel.view.clearAllShown)
    }

    @Test
    fun speechButtonEnabled_whenDeviceCompatible() {

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            isSpeechCompatible = true
        )

        assertTrue(homeViewModel.view.voiceButton)
    }

    @Test
    fun speechButtonDisabled_whenDeviceIsNotCompatible() {

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            isSpeechCompatible = false
        )

        assertFalse(homeViewModel.view.voiceButton)
    }
}