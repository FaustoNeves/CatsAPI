package com.fausto.network.di

import com.fausto.network.service.CatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
internal object CatsServiceModule {

    @Provides
    fun provideCatsService(): CatsService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/").client(
            OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        ).addConverterFactory(GsonConverterFactory.create()).build().create(CatsService::class.java)
    }
}