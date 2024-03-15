package com.fausto.domain.di

import com.fausto.domain.usecase.GetBreedByIdUseCase
import com.fausto.domain.usecase.GetBreedByIdUseCaseImpl
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsBySearchUseCaseImpl
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.domain.usecase.GetBreedsUseCaseImpl
import com.fausto.domain.usecase.GetImagesByIdUseCase
import com.fausto.domain.usecase.GetImagesByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsGetBreedsUseCase(useCase: GetBreedsUseCaseImpl): GetBreedsUseCase

    @Binds
    abstract fun bindGetBreedsBySearchUseCase(useCaseImpl: GetBreedsBySearchUseCaseImpl): GetBreedsBySearchUseCase

    @Binds
    abstract fun bindsGetBreedByIdUseCase(useCase: GetBreedByIdUseCaseImpl): GetBreedByIdUseCase

    @Binds
    abstract fun bindsGetImagesByIdUseCase(useCase: GetImagesByIdUseCaseImpl): GetImagesByIdUseCase
}