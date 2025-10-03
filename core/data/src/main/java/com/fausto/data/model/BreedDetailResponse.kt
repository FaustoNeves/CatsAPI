package com.fausto.data.model
import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BreedDetailResponse(
    val weight: WeightResponse,
    val id: String?,
    val name: String?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    @SerialName("life_span") val lifeSpan: String?,
)
