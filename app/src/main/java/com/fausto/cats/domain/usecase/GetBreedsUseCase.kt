package com.fausto.cats.domain.usecase

import com.fausto.cats.domain.mapper.toModel
import com.fausto.cats.domain.repository.CatsRepository
import com.fausto.model.BreedsModel
import javax.inject.Inject

internal fun interface GetBreedsUseCase {
    suspend fun getBreeds(): List<BreedsModel>
}

internal class GetBreedsUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedsUseCase {
    override suspend fun getBreeds(): List<BreedsModel> {
        return catsRepository.getBreeds().map { it.toModel() }
    }
}