package com.fausto.network.model

import com.google.gson.annotations.SerializedName

data class BreedsResponse(
    @SerializedName("reference_image_id") val id: String?, val name: String?
)