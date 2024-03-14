package com.fausto.cats.domain.mapper

import com.fausto.cats.data.response.BreedImageResponse
import com.fausto.cats.data.response.BreedsResponse
import com.fausto.model.BreedImageModel
import com.fausto.model.BreedsModel

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

