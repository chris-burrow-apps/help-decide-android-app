package com.chrisburrow.helpdecide.ui.libraries.preferences

import android.content.Context
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookupInterface.Companion.INSTANT_KEY
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookupInterface.Companion.PICK_A_BUBBLE_KEY
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookupInterface.Companion.SPIN_THE_WHEEL_KEY

interface DecisionTypeLookupInterface {

    fun getDecisionTypeTitle(key: String) : String

    companion object {

        const val SPIN_THE_WHEEL_KEY = "spinTheWheel"
        const val INSTANT_KEY = "instant"
        const val PICK_A_BUBBLE_KEY = "pickABubble"
    }
}

class DecisionTypeLookup(val context: Context) : DecisionTypeLookupInterface {

    val options: LinkedHashMap<String, String> = linkedMapOf(
        SPIN_THE_WHEEL_KEY to context.getString(R.string.spin_the_wheel),
        PICK_A_BUBBLE_KEY to context.getString(R.string.pick_a_bubble),
        INSTANT_KEY to context.getString(R.string.instant_decision),
    )

    override fun getDecisionTypeTitle(key: String) : String {

        return options.getValue(key) ?: ""
    }


}