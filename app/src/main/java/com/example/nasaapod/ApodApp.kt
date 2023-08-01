package com.example.nasaapod

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nasaapod.ui.ApodUiState
import com.example.nasaapod.ui.ApodViewModel
import com.example.nasaapod.ui.DisplayApod
import com.example.nasaapod.ui.Loading

@Composable
fun ApodApp() {

    val apodViewModel: ApodViewModel = viewModel()

    when (val apodUiState = apodViewModel.apodUiState) {

        is ApodUiState.Loading -> Loading()

        is ApodUiState.Error -> Text(text = "Error")

        is ApodUiState.Success -> DisplayApod(
            title = apodUiState.title,
            date = apodUiState.date,
            url = apodUiState.url,
            copyright = apodUiState.copyright,
            description = apodUiState.description
        )

    }
}