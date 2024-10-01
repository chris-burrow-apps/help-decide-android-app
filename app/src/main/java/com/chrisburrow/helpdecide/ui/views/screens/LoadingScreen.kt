package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme


@Composable
fun LoadingScreen() {

    Box(modifier = Modifier.fillMaxSize()) {

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@ThemePreviews
@Composable
fun LoadingScreenPreview() {

    HelpDecideTheme {

        LoadingScreen()
    }
}