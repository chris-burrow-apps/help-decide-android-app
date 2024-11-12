package com.chrisburrow.helpdecide.utils.speechtotext

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.util.Locale

@Composable
fun SpeechToText(response: (String) -> Unit, cancelled: () -> Unit) {

    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    intent.putExtra(

        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )

    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak one of the options")

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {

        when {
            it.resultCode != Activity.RESULT_OK -> {

                cancelled()
            } else -> {

                val result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                if (result !== null) {

                    response(result.first())
                } else {

                    cancelled()
                }
            }
        }
    }

    LaunchedEffect(Unit) {

        launcher.launch(intent)
    }
}