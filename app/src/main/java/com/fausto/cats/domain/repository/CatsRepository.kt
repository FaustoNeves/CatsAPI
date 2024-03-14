package com.fausto.cats.domain.repository

import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedResponse
import com.fausto.network.model.BreedsResponse

internal interface CatsRepository {
    suspend fun getBreeds(): List<BreedsResponse>
    suspend fun getBreedsBySearch(breedQuery: String): List<BreedsResponse>
    suspend fun getBreedById(breedId: String): BreedResponse
    suspend fun getImagesById(
        breedId: String
    ): List<BreedImageResponse>
}