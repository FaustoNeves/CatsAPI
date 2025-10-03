package com.fausto.data.repository

import com.fausto.model.BreedsModel
import com.fausto.data.mapper.toModel
import com.fausto.data.model.BreedImageResponse
import com.fausto.data.model.BreedResponse
import com.fausto.data.service.CatsService
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val catsService: CatsService,
) : CatsRepository {

    override suspend fun getBreeds(): List<BreedsModel> =
        catsService.getBreeds().map { it.toModel() }

    override suspend fun getBreedsBySearch(breedQuery: String) =
        catsService.getBreedsBySearch(breedQuery)

    override suspend fun getBreedById(
        breedId: String
    ): BreedResponse = catsService.getBreedById(breedId)

    override suspend fun getImagesById(breedId: String): List<BreedImageResponse> =
        catsService.getImagesById(breedId = breedId)
}