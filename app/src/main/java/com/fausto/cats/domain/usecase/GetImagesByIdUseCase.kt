package com.fausto.cats.domain.usecase

import com.fausto.cats.domain.mapper.toModel
import com.fausto.cats.domain.repository.CatsRepository
import com.fausto.model.BreedImageModel
import javax.inject.Inject

internal fun interface GetImagesByIdUseCase {
    suspend fun getImagesById(breedId: String): List<BreedImageModel>
}

internal class GetImagesByIdUseCaseImpl @Inject constructor(private val catsRepository: CatsRepository) :
    GetImagesByIdUseCase {
    override suspend fun getImagesById(breedId: String): List<BreedImageModel> {
        return catsRepository.getImagesById(breedId).map { it.toModel() }
    }
}