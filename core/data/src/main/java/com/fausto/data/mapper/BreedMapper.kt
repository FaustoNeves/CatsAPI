package com.fausto.data.mapper

import com.fausto.model.BreedDetailModel
import com.fausto.model.BreedModel
import com.fausto.model.WeightModel
import com.fausto.data.model.BreedDetailResponse
import com.fausto.data.model.BreedResponse
import com.fausto.data.model.WeightResponse

fun BreedResponse.toModel(): BreedModel {
    return BreedModel(id = id ?: "",
        url = url ?: "",
        breedDetails = breeds.first().toModel()
    )
}

fun BreedDetailResponse.toModel(): BreedDetailModel {
    return BreedDetailModel(
        weight = weight.toModel(),
        id = id ?: "",
        name = name ?: "",
        temperament = temperament ?: "",
        origin = origin ?: "",
        description = description ?: "",
        lifeSpan = lifeSpan ?: ""
    )
}

fun WeightResponse.toModel(): WeightModel {
    return WeightModel(
        imperial = imperial ?: "N/A", metric = metric ?: "N/A"
    )
}