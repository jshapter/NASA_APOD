package com.example.nasaapod.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nasaapod.ApodApp

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Oops, something went wrong...",
                fontSize = 22.sp
            )
            Text(
                modifier = Modifier
                    .padding(top = 24.dp),
                //    .clickable { ApodApp() },
                text = "Try again later"
            )
        }
    }
}