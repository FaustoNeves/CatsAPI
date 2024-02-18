package com.fausto.cats.data.response

internal data class BreedResponse(
    val id: String?, val url: String?, val breeds: List<BreedDetailResponse>?
)
