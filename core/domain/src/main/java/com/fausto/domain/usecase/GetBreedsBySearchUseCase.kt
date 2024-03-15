package com.fausto.domain.usecase

import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedsModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetBreedsBySearchUseCase {
    suspend fun getBreedsBySearch(breedQuery: String): List<BreedsModel>
}

class GetBreedsBySearchUseCaseImpl @Inject constructor(
    private val catsRepository: CatsRepository
) : GetBreedsBySearchUseCase {
    override suspend fun getBreedsBySearch(breedQuery: String): List<BreedsModel> {
        return catsRepository.getBreedsBySearch(breedQuery).map { it.toModel() }
    }
}