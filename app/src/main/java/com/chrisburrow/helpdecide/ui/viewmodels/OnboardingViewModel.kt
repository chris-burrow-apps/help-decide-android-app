package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface

data class OnboardingPage(
    var viewTitle: String,
    var viewDescription: String,
    var viewImage: Int,
    var viewNextButton: String,
    var viewNextPressed: () -> Unit,
    var viewSkipButton: String,
    var viewSkipPressed: () -> Unit
)

class OnboardingViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val pages: List<OnboardingPage>
): AnalyticsViewModel(analyticsLibrary) {

    fun getPageDetails(position: Int) : OnboardingPage {

        return pages[position]
    }

    fun onNextPressed(position: Int) : () -> Unit {

        return pages[position].viewNextPressed
    }

    fun onSkipPressed(position: Int) : () -> Unit {

        return pages[position].viewSkipPressed
    }
}