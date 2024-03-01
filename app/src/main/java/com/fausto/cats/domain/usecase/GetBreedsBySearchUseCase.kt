package com.fausto.cats.domain.usecase

import com.fausto.cats.domain.mapper.toModel
import com.fausto.cats.domain.model.BreedsModel
import com.fausto.cats.domain.repository.CatsRepository
import javax.inject.Inject

internal fun interface GetBreedsBySearchUseCase {
    suspend fun getBreedsBySearch(breedQuery: String): List<BreedsModel>
}

internal class GetBreedsBySearchUseCaseImpl @Inject constructor(
    private val catsRepository: CatsRepository
) : GetBreedsBySearchUseCase {
    override suspend fun getBreedsBySearch(breedQuery: String): List<BreedsModel> {
        return catsRepository.getBreedsBySearch(breedQuery).map { it.toModel() }
    }
}