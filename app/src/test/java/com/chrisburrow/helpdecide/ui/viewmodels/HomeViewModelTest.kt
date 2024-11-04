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

        assertFalse(homeViewModel.view.value.decideOption)
        assertFalse(homeViewModel.view.value.clearAllShown)
        assertTrue(homeViewModel.view.value.emptyView)

        assertEquals(0, homeViewModel.view.value.options.size)
    }

    @Test
    fun addOption() {

        val homeViewModel = HomeViewModel(analyticsLibrary = MockAnalyticsLibrary())

        val newOption = OptionObject(text = "Option 1")

        homeViewModel.addOption(newOption)

        assertTrue(homeViewModel.view.value.options.contains(newOption))
        assertFalse(homeViewModel.view.value.decideOption)
        assertFalse(homeViewModel.view.value.emptyView)
        assertTrue(homeViewModel.view.value.clearAllShown)

        val anotherOption = OptionObject(text = "Option 2")

        homeViewModel.addOption(anotherOption)

        assertTrue(homeViewModel.view.value.options.contains(anotherOption))
        assertTrue(homeViewModel.view.value.decideOption)
        assertFalse(homeViewModel.view.value.emptyView)
        assertTrue(homeViewModel.view.value.clearAllShown)
    }

    @Test
    fun deleteOption() {

        val options = List(5) { OptionObject(text = "$it") }

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            initialOptions = options
        )

        assertEquals(options, homeViewModel.view.value.options)

        val deleteOption = options[0]

        homeViewModel.deleteOption(deleteOption.id)

        assertFalse(homeViewModel.view.value.options.contains(deleteOption))
        assertTrue(homeViewModel.view.value.decideOption)
        assertFalse(homeViewModel.view.value.emptyView)
    }

    @Test
    fun clearOptions() {

        val options = List(5) { OptionObject(text = "$it") }

        val homeViewModel = HomeViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            initialOptions = options
        )

        assertEquals(options, homeViewModel.view.value.options)

        homeViewModel.clearOptions()

        assertEquals(0, homeViewModel.view.value.options.size)
        assertFalse(homeViewModel.view.value.decideOption)
        assertTrue(homeViewModel.view.value.emptyView)
        assertFalse(homeViewModel.view.value.clearAllShown)
    }
}