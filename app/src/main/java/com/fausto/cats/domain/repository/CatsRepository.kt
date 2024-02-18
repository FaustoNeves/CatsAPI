package com.fausto.cats.domain.repository

import com.fausto.cats.data.response.BreedImageResponse
import com.fausto.cats.data.response.BreedResponse
import com.fausto.cats.data.response.BreedsResponse
import retrofit2.http.Query

internal interface CatsRepository {
    suspend fun getBreeds(): List<BreedsResponse>
    suspend fun getBreedById(breedId: String): BreedResponse
    suspend fun getImagesById(
        breedId: String
    ): List<BreedImageResponse>
}