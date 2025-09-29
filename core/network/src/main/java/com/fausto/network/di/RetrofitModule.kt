package com.fausto.network.di

import com.fausto.network.service.CatsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object CatsServiceModule {

    @Provides
    fun provideCatsService(): CatsService {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/").client(
            OkHttpClient.Builder()
                .build()
        ).addConverterFactory(json.asConverterFactory(contentType)).build()
            .create(CatsService::class.java)
    }
}