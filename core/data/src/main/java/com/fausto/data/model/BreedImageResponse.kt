package com.fausto.data.model
import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BreedImageResponse(
    val url: String?
)
