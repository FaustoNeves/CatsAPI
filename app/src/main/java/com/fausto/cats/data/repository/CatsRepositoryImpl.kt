package com.fausto.cats.data.repository

import com.fausto.cats.domain.repository.CatsRepository
import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedResponse
import com.fausto.network.model.BreedsResponse
import com.fausto.network.service.CatsService
import javax.inject.Inject

internal class CatsRepositoryImpl @Inject constructor(
    private val catsService: CatsService,
) : CatsRepository {

    override suspend fun getBreeds(): List<BreedsResponse> = catsService.getBreeds()
    override suspend fun getBreedsBySearch(breedQuery: String) =
        catsService.getBreedsBySearch(breedQuery)

    override suspend fun getBreedById(
        breedId: String
    ): BreedResponse = catsService.getBreedById(breedId)

    override suspend fun getImagesById(breedId: String): List<BreedImageResponse> =
        catsService.getImagesById(breedId = breedId)
}