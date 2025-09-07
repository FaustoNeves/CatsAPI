package com.fausto.network.model

data class BreedResponse(
    val id: String?, val url: String?, val breeds: List<BreedDetailResponse>
)
