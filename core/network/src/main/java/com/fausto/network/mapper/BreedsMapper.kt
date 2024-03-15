package com.fausto.network.mapper

import com.fausto.model.BreedImageModel
import com.fausto.model.BreedsModel
import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedsResponse

fun BreedImageResponse.toModel(): BreedImageModel {
    return BreedImageModel(
        url = url ?: ""
    )
}

fun BreedsResponse.toModel(): BreedsModel {
    return BreedsModel(
        section = name?.first().toString(), id = id ?: "", name = name ?: ""
    )
}

