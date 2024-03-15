package com.fausto.data.di

import com.fausto.data.repository.CatsRepository
import com.fausto.data.repository.CatsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository
}
