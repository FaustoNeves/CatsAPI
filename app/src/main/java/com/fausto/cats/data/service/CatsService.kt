package com.fausto.cats.data.service

import com.fausto.cats.data.response.BreedImageResponse
import com.fausto.cats.data.response.BreedResponse
import com.fausto.cats.data.response.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CatsService {

    @GET("breeds")
    suspend fun getBreeds(): List<BreedsResponse>

    @GET("images/{imageId}")
    suspend fun getBreedById(
        @Path("imageId") breedId: String
    ): BreedResponse

    @GET("images/search")
    suspend fun getImagesById(
        @Query("limit") limit: Int = 10,
        @Query("breed_id") breedId: String,
    ): List<BreedImageResponse>
}