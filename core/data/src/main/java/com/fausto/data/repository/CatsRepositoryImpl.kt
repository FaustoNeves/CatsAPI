package com.fausto.data.repository

import com.fausto.model.BreedsModel
import com.fausto.network.mapper.toModel
import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedResponse
import com.fausto.network.service.CatsService
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