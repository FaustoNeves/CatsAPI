package com.fausto.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BreedsResponse(
    @SerialName("id") val queryBreedId: String? = null,
    @SerialName("reference_image_id") val referenceImageId: String? = null,
    val name: String? = null
)