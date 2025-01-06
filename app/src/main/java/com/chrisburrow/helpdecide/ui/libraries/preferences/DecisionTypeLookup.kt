package com.chrisburrow.helpdecide.ui.libraries.preferences

import android.content.Context
import com.chrisburrow.helpdecide.R

class DecisionTypeLookup() {

    companion object {

        const val SPIN_THE_WHEEL_KEY = "spinTheWheel"
        const val INSTANT_KEY = "instant"
        const val PICK_A_BUBBLE_KEY = "pickABubble"

        fun options(context: Context) = linkedMapOf(
            SPIN_THE_WHEEL_KEY to context.getString(R.string.spin_the_wheel),
            PICK_A_BUBBLE_KEY to context.getString(R.string.pick_a_bubble),
            INSTANT_KEY to context.getString(R.string.instant_decision),
        )
    }
}