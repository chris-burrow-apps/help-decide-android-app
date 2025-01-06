package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface

@Composable
fun DecisionFlowScreen(
    preferencesLibrary: PreferencesLibraryInterface,
    forceSkipDecisionTypeDialog: Boolean = false,
    showDecisionType:() -> Unit,
    showInstantDecision: () -> Unit,
    showPopTheBubble:() -> Unit,
    showSpinTheWheel:() -> Unit,
) {

    LaunchedEffect(Unit) {

        val shouldSkipOptionDialog = preferencesLibrary.shouldSkipDecisionType()

        if (shouldSkipOptionDialog || forceSkipDecisionTypeDialog) {

            val decisionType = preferencesLibrary.checkDefaultDecisionOption()

            when (decisionType) {

                DecisionTypeLookup.SPIN_THE_WHEEL_KEY -> {

                    showSpinTheWheel()
                }

                DecisionTypeLookup.INSTANT_KEY -> {

                    showInstantDecision()
                }

                DecisionTypeLookup.PICK_A_BUBBLE_KEY -> {

                    showPopTheBubble()
                }
            }
        } else {

            showDecisionType()
        }
    }
}