package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.chrisburrow.helpdecide.ui.NavigationScreenItem
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.navigateAndPopUpTo
import kotlinx.coroutines.launch

abstract class LoadingViewModel : ViewModel() {

    abstract fun loadData()
}

class AppStartupViewModel(
    val navController: NavController,
    val analyticsLibrary: AnalyticsLibraryInterface
) : LoadingViewModel() {

    override fun loadData() {

        viewModelScope.launch {

            analyticsLibrary.checkSettingsShown().collect { settingsPreviouslyShown ->

                val navigateTo = if (settingsPreviouslyShown) {

                    NavigationScreenItem.Home.route
                } else {

                    NavigationScreenItem.Onboarding.route
                }

                navController.navigateAndPopUpTo(navigateTo)
            }
        }
    }
}