package com.example.nasaapod

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nasaapod.ui.theme.ApodUiState
import com.example.nasaapod.ui.theme.ApodViewModel

@Composable
fun ApodApp() {
    val apodViewModel: ApodViewModel = viewModel()
    val apodUiState = apodViewModel.apodUiState

    when (apodUiState) {
        is ApodUiState.Loading -> Text(text = "Loading")
        is ApodUiState.Success -> Text(text = apodUiState.msg)
        is ApodUiState.Error -> Text(text = "Error")
    }
}