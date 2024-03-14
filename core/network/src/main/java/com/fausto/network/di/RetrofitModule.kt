package com.fausto.network.di

import com.fausto.network.service.CatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
internal object CatsServiceModule {

    @Provides
    fun provideCatsService(): CatsService =
        Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CatsService::class.java)
}