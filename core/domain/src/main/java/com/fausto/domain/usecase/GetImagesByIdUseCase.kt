package com.fausto.domain.usecase

import com.fausto.data.repository.CatsRepository
import com.fausto.model.BreedImageModel
import com.fausto.network.mapper.toModel
import javax.inject.Inject

fun interface GetImagesByIdUseCase {
    suspend fun getImagesById(breedId: String): List<BreedImageModel>
}

class GetImagesByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetImagesByIdUseCase {
    override suspend fun getImagesById(breedId: String): List<BreedImageModel> {
        return catsRepository.getImagesById(breedId).map { it.toModel() }
    }
}