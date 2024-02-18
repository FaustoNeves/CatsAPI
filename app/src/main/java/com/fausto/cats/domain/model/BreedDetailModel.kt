package com.fausto.cats.domain.model

internal data class BreedDetailModel(
    val weight: WeightModel?,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val lifeSpan: String,
)
