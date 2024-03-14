package com.fausto.cats.domain.mapper

import com.fausto.model.BreedImageModel
import com.fausto.model.BreedsModel
import com.fausto.network.model.BreedImageResponse
import com.fausto.network.model.BreedsResponse

internal fun BreedImageResponse.toModel(): BreedImageModel {
    return BreedImageModel(
        url = url ?: ""
    )
}

internal fun BreedsResponse.toModel(): BreedsModel {
    return BreedsModel(
        section = name?.first().toString(), id = id ?: "", name = name ?: ""
    )
}

