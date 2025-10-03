package com.fausto.data.model
import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WeightResponse(
    val imperial: String?, val metric: String?
)
