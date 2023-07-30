package com.example.nasaapod

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nasaapod.data.Apod
import com.example.nasaapod.ui.theme.ApodUiState
import com.example.nasaapod.ui.theme.ApodViewModel

@Composable
fun ApodApp() {
    val apodViewModel: ApodViewModel = viewModel()

    when (val apodUiState = apodViewModel.apodUiState) {
        is ApodUiState.Loading -> Text(text = "Loading")
        is ApodUiState.Success -> DisplayApod(apod = apodUiState.apod)
        is ApodUiState.Error -> Text(text = "Error")
    }
}

@Composable
fun DisplayApod(apod: Apod) {
    Column() {
        AsyncImage(
            model = apod.hdurl,
            contentDescription = "")
    }
}