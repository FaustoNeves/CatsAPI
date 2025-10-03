package com.fausto.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BreedResponse(
    val id: String?, val url: String?, val breeds: List<BreedDetailResponse>
)
