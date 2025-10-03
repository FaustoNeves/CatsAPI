package com.fausto.data.repository

import com.fausto.model.BreedsModel
import com.fausto.data.model.BreedImageResponse
import com.fausto.data.model.BreedResponse
import com.fausto.data.model.BreedsResponse

interface CatsRepository {
    suspend fun getBreeds(): List<BreedsModel>
    suspend fun getBreedsBySearch(breedQuery: String): List<BreedsResponse>
    suspend fun getBreedById(breedId: String): BreedResponse
    suspend fun getImagesById(
        breedId: String
    ): List<BreedImageResponse>
}