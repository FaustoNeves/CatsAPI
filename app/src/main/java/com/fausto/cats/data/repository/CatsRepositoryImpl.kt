package com.fausto.cats.data.repository

import com.fausto.cats.data.response.BreedImageResponse
import com.fausto.cats.data.response.BreedResponse
import com.fausto.cats.data.response.BreedsResponse
import com.fausto.cats.data.service.CatsService
import com.fausto.cats.domain.repository.CatsRepository
import javax.inject.Inject

internal class CatsRepositoryImpl @Inject constructor(
    private val catsService: CatsService,
) : CatsRepository {

    override suspend fun getBreeds(): List<BreedsResponse> = catsService.getBreeds()

    override suspend fun getBreedById(
        breedId: String
    ): BreedResponse = catsService.getBreedById(breedId)

    override suspend fun getImagesById(breedId: String): List<BreedImageResponse> =
        catsService.getImagesById(breedId = breedId)
}