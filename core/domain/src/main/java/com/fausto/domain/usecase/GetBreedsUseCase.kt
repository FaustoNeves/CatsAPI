package com.fausto.domain.usecase

import com.fausto.common.result.ResultWrapper
import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedsModel
import javax.inject.Inject

interface GetBreedsUseCase {
    suspend fun getStateFlowBreeds(): ResultWrapper<List<BreedsModel>>
    suspend fun getBreedsWithinCoroutines(): ResultWrapper<List<BreedsModel>>
}

class GetBreedsUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetBreedsUseCase {
    override suspend fun getStateFlowBreeds(): ResultWrapper<List<BreedsModel>> {
        return ResultWrapper.Success(catsRepository.getBreeds())
//        return flow {
//            val breeds = catsRepository.getBreeds()
//            emit(ResultWrapper.Success(breeds))
//        }
    }

    override suspend fun getBreedsWithinCoroutines(): ResultWrapper<List<BreedsModel>> {
        return try {
            val breeds = catsRepository.getBreeds()
            ResultWrapper.Success(breeds)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}