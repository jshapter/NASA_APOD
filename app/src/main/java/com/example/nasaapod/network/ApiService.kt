package com.example.nasaapod.network

import com.example.nasaapod.data.Apod
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofitApi = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.nasa.gov")
    .build()

interface ApodApiService {
    @GET("/planetary/apod?api_key=vUFLHpbyfg1kmhcblxfulV8X4QEXvb3ghuFqwPfS")
    suspend fun getApod(): Apod
}

object ApodApi {
    val retrofitService: ApodApiService by lazy {
        retrofitApi.create(ApodApiService::class.java)
    }
}