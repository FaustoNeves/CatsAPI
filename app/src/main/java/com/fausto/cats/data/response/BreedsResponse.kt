package com.fausto.cats.data.response

import com.google.gson.annotations.SerializedName


internal data class BreedsResponse(
    @SerializedName("reference_image_id") val id: String?, val name: String?
)