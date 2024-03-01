package com.fausto.cats.di

import com.fausto.cats.data.repository.CatsRepositoryImpl
import com.fausto.cats.domain.repository.CatsRepository
import com.fausto.cats.domain.usecase.GetBreedByIdUseCase
import com.fausto.cats.domain.usecase.GetBreedByIdUseCaseImpl
import com.fausto.cats.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.cats.domain.usecase.GetBreedsBySearchUseCaseImpl
import com.fausto.cats.domain.usecase.GetBreedsUseCase
import com.fausto.cats.domain.usecase.GetBreedsUseCaseImpl
import com.fausto.cats.domain.usecase.GetImagesByIdUseCase
import com.fausto.cats.domain.usecase.GetImagesByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class CatsModule {

    @Binds
    abstract fun bindCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository


    @Binds
    abstract fun bindsGetBreedsUseCase(useCase: GetBreedsUseCaseImpl): GetBreedsUseCase

    @Binds
    abstract fun bindGetBreedsBySearchUseCase(useCaseImpl: GetBreedsBySearchUseCaseImpl): GetBreedsBySearchUseCase

    @Binds
    abstract fun bindsGetBreedByIdUseCase(useCase: GetBreedByIdUseCaseImpl): GetBreedByIdUseCase

    @Binds
    abstract fun bindsGetImagesByIdUseCase(useCase: GetImagesByIdUseCaseImpl): GetImagesByIdUseCase
}