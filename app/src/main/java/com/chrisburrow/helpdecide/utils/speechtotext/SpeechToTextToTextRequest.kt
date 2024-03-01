package com.chrisburrow.helpdecide.utils.speechtotext

import android.content.Context
import android.speech.SpeechRecognizer

class SpeechToTextToTextRequest(private val context: Context) : SpeechToTextInterface {
    override fun isSpeechCompatible(): Boolean {


        return SpeechRecognizer.isRecognitionAvailable(context)
    }
}

interface SpeechToTextInterface {
    
    fun isSpeechCompatible() : Boolean
}