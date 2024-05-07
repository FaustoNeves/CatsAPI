package com.fausto.domain.usecase

import com.fausto.common.result.ResultWrapper
import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedImageModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetImagesByIdUseCase {
    suspend fun getImagesById(breedId: String): ResultWrapper<List<BreedImageModel>>
}

class GetImagesByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetImagesByIdUseCase {
    override suspend fun getImagesById(breedId: String): ResultWrapper<List<BreedImageModel>> {
        return try {
            val breedImageModel = catsRepository.getImagesById(breedId).map { it.toModel() }
            ResultWrapper.Success(breedImageModel)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}