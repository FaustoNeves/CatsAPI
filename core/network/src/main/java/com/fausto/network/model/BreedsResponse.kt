package com.fausto.network.model

import com.google.gson.annotations.SerializedName

data class BreedsResponse(
    @SerializedName("id") val queryBreedId: String?,
    @SerializedName("reference_image_id") val referenceImageId: String?,
    val name: String?
)