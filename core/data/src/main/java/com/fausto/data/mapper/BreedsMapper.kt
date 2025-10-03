package com.fausto.data.mapper

import com.fausto.model.BreedImageModel
import com.fausto.model.BreedsModel
import com.fausto.data.model.BreedImageResponse
import com.fausto.data.model.BreedsResponse

fun BreedImageResponse.toModel(): BreedImageModel {
    return BreedImageModel(
        url = url ?: ""
    )
}

fun BreedsResponse.toModel(): BreedsModel {
    return BreedsModel(
        section = name?.firstOrNull().toString(),
        queryBreedId = queryBreedId ?: "",
        referenceImageId = referenceImageId ?: "",
        name = name ?: ""
    )
}

