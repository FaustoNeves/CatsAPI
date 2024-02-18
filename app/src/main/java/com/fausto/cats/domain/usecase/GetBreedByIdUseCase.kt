package com.fausto.cats.domain.usecase

import com.fausto.cats.domain.mapper.toModel
import com.fausto.cats.domain.model.BreedModel
import com.fausto.cats.domain.repository.CatsRepository
import javax.inject.Inject

internal fun interface GetBreedByIdUseCase {
    suspend fun getBreedById(breedId: String): BreedModel
}

internal class GetBreedByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedByIdUseCase {
    override suspend fun getBreedById(breedId: String): BreedModel {
        return catsRepository.getBreedById(breedId).toModel()
    }
}