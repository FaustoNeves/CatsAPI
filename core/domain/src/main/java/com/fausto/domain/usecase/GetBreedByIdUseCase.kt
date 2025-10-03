package com.fausto.domain.usecase

import com.fausto.common.result.ResultWrapper
import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedModel
import com.fausto.data.mapper.toModel
import javax.inject.Inject

fun interface GetBreedByIdUseCase {
    suspend fun getBreedById(breedId: String): ResultWrapper<BreedModel>
}

class GetBreedByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedByIdUseCase {
    override suspend fun getBreedById(breedId: String): ResultWrapper<BreedModel> {
        return try {
            val breedModel = catsRepository.getBreedById(breedId).toModel()
            ResultWrapper.Success(breedModel)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}