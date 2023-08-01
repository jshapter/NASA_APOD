package com.example.nasaapod.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.network.ApodApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class ApodUiState {
    data class Success(
        val title: String,
        val date: String,
        val url: String,
        val copyright: String,
        val description: String
        ) : ApodUiState()
    object Error : ApodUiState()
    object Loading : ApodUiState()
}

class ApodViewModel : ViewModel() {
    var apodUiState: ApodUiState by mutableStateOf(ApodUiState.Loading)
        private set

    init {
        getCurrentApod()
    }

    private fun getCurrentApod() {
        viewModelScope.launch {
            apodUiState = ApodUiState.Loading
            apodUiState = try {

                val key = "vUFLHpbyfg1kmhcblxfulV8X4QEXvb3ghuFqwPfS"
                val apodResult = ApodApi.retrofitService.getApod(key)

                val title = apodResult.title
                val apodDate = apodResult.date
                val url = apodResult.hdurl
                var copyright = apodResult.copyright
                val description = apodResult.explanation

                val toDate = LocalDate.parse(apodDate)
                val dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                val date = toDate.format(dateFormatter)

                // Incorrect warning for condition always being false. It can be.
                if (apodResult.copyright == null) {
                    copyright = "null"
                }

                ApodUiState.Success(
                    title,
                    date,
                    url,
                    copyright,
                    description
                )

            } catch (e: IOException) {
                ApodUiState.Error
            } catch (e: HttpException) {
                ApodUiState.Error
            }
        }
    }
}