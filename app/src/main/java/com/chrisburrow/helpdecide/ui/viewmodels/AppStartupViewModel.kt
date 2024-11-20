package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.chrisburrow.helpdecide.ui.NavigationScreenItem
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import com.chrisburrow.helpdecide.ui.navigateAndPopUpTo
import kotlinx.coroutines.launch

abstract class LoadingViewModel : ViewModel() {

    abstract fun loadData()
}

class AppStartupViewModel(
    private val navController: NavController,
    private val preferencesLibrary: PreferencesLibraryInterface,
) : LoadingViewModel() {

    override fun loadData() {

        viewModelScope.launch {

            val navigateTo = if (preferencesLibrary.checkPermissionsShown()) {

                NavigationScreenItem.Home.route
            } else {

                NavigationScreenItem.Onboarding.route
            }

            navController.navigateAndPopUpTo(navigateTo)
        }
    }
}