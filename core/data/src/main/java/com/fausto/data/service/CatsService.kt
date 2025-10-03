package com.fausto.data.service

import com.fausto.data.model.BreedImageResponse
import com.fausto.data.model.BreedResponse
import com.fausto.data.model.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatsService {

    @GET("breeds")
    suspend fun getBreeds(): List<BreedsResponse>

    @GET("breeds/search")
    suspend fun getBreedsBySearch(@Query("q") breedQuery: String): List<BreedsResponse>

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