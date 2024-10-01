package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.screens.OnboardingTags

class OnboardingRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.onNodeWithTag(OnboardingTags.BASE_VIEW_TAG).assertIsDisplayed()
    }

    fun pressSkip(position: Int) { rule.onNodeWithTag(OnboardingTags.SKIP_VIEW_TAG + position).performClick() }

    fun pressNext(position: Int) { rule.onNodeWithTag(OnboardingTags.NEXT_VIEW_TAG + position).performClick() }

    fun checkSkipHidden(position: Int) { rule.onNodeWithTag(OnboardingTags.SKIP_VIEW_TAG + position).assertDoesNotExist() }

    fun checkNextHidden(position: Int) { rule.onNodeWithTag(OnboardingTags.NEXT_VIEW_TAG + position).assertDoesNotExist() }

    fun checkNextText(position: Int, text: String) {

        rule.onNodeWithTag(OnboardingTags.NEXT_VIEW_TAG + position)
            .assertTextContains(text)
    }

    fun checkSkipText(position: Int, text: String) {

        rule.onNodeWithTag(OnboardingTags.SKIP_VIEW_TAG + position)
            .assertTextContains(text)
    }
}

fun onboarding(rule: ComposeContentTestRule, block: OnboardingRobot.() -> Unit) = OnboardingRobot(rule).apply(block)