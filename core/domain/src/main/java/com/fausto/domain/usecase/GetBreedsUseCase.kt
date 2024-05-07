package com.fausto.domain.usecase

import com.fausto.common.result.ResultWrapper
import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedsModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetBreedsUseCase {
    suspend fun getBreeds(): ResultWrapper<List<BreedsModel>>
}

class GetBreedsUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedsUseCase {
    override suspend fun getBreeds(): ResultWrapper<List<BreedsModel>> {
        return try {
            val breeds = catsRepository.getBreeds().map { it.toModel() }
            ResultWrapper.Success(breeds)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}