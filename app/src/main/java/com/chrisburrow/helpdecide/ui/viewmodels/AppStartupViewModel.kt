package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.chrisburrow.helpdecide.ui.NavigationScreenItem
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.navigateAndPopUpTo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class LoadingViewModel : ViewModel() {

    abstract fun loadData()
}

class AppStartupViewModel(
    private val navController: NavController,
    val analyticsLibrary: AnalyticsLibraryInterface
) : LoadingViewModel() {

    override fun loadData() {

        viewModelScope.launch {

            val navigateTo = if (analyticsLibrary.checkSettingsShown()) {

                NavigationScreenItem.Home.route
            } else {

                NavigationScreenItem.Onboarding.route
            }

            navController.navigateAndPopUpTo(navigateTo)
        }
    }
}