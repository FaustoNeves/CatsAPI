package com.fausto.datastore.di

import android.content.Context
import com.fausto.datastore.querybreed.BreedIdsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QueryBreedIdManagerModule {

    @Provides
    @Singleton
    fun providesDataStoreManager(
        @ApplicationContext context: Context
    ) = BreedIdsManager(context)
}