package com.fausto.datastore.di

import android.content.Context
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.datastore.querybreed.BreedIdsManagerImpl
import dagger.Binds
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
    fun providesDataStoreManagerImpl(
        @ApplicationContext context: Context
    ) = BreedIdsManagerImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class QueryBreedIdManagerInterface {
    @Binds
    abstract fun providesQueryBreedIdManager(breedIdsManager: BreedIdsManagerImpl): BreedIdsManager
}