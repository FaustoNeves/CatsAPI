package com.fausto.data.repository

import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedResponse
import com.fausto.network.model.BreedsResponse

interface CatsRepository {
    suspend fun getBreeds(): List<BreedsResponse>
    suspend fun getBreedsBySearch(breedQuery: String): List<BreedsResponse>
    suspend fun getBreedById(breedId: String): BreedResponse
    suspend fun getImagesById(
        breedId: String
    ): List<BreedImageResponse>
}