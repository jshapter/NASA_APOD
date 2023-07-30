package com.example.nasaapod.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.data.Apod
import com.example.nasaapod.network.ApodApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ApodUiState {
    data class Success(val apod: Apod) : ApodUiState()
    object Error : ApodUiState()
    object Loading : ApodUiState()
}

class ApodViewModel : ViewModel() {
    var apodUiState: ApodUiState by mutableStateOf(ApodUiState.Loading)
        private set

    init {
        getCurrentApod()
    }

    fun getCurrentApod() {
        viewModelScope.launch {
            apodUiState = ApodUiState.Loading
            apodUiState = try {
                val apodResult = ApodApi.retrofitService.getApod()
                ApodUiState.Success(
                    apodResult
                )
            } catch (e: IOException) {
                ApodUiState.Error
            } catch (e: HttpException) {
                ApodUiState.Error
            }
        }
    }
}