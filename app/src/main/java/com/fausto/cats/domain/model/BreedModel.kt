package com.fausto.cats.domain.model

internal data class BreedModel(
    val id: String, val url: String, val breeds: List<BreedDetailModel>?
)
