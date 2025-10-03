package com.fausto.domain.usecase

import com.fausto.common.result.ResultWrapper
import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedsModel
import com.fausto.data.mapper.toModel
import javax.inject.Inject

fun interface GetBreedsBySearchUseCase {
    suspend fun getBreedsBySearch(breedQuery: String): ResultWrapper<List<BreedsModel>>
}

class GetBreedsBySearchUseCaseImpl @Inject constructor(
    private val catsRepository: CatsRepository
) : GetBreedsBySearchUseCase {
    override suspend fun getBreedsBySearch(breedQuery: String): ResultWrapper<List<BreedsModel>> {
        return try {
            val breedsModel = catsRepository.getBreedsBySearch(breedQuery).map { it.toModel() }
            ResultWrapper.Success(breedsModel)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}