package com.fausto.domain.usecase

import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetBreedByIdUseCase {
    suspend fun getBreedById(breedId: String): BreedModel
}

class GetBreedByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedByIdUseCase {
    override suspend fun getBreedById(breedId: String): BreedModel {
        return catsRepository.getBreedById(breedId).toModel()
    }
}