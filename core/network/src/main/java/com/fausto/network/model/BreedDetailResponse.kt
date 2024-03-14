package com.fausto.network.model

import com.google.gson.annotations.SerializedName

data class BreedDetailResponse(
    val weight: WeightResponse?,
    val id: String?,
    val name: String?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    @SerializedName("life_span") val lifeSpan: String?,
)
