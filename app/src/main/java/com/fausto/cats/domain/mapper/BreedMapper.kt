package com.fausto.cats.domain.mapper

import com.fausto.model.BreedDetailModel
import com.fausto.model.BreedModel
import com.fausto.model.WeightModel
import com.fausto.network.model.BreedDetailResponse
import com.fausto.network.model.BreedResponse
import com.fausto.network.model.WeightResponse

internal fun BreedResponse.toModel(): BreedModel {
    return BreedModel(id = id ?: "",
        url = url ?: "",
        breeds = breeds?.map { it.toModel() } ?: emptyList())
}

internal fun BreedDetailResponse.toModel(): BreedDetailModel {
    return BreedDetailModel(
        weight = weight?.toModel(),
        id = id ?: "",
        name = name ?: "",
        temperament = temperament ?: "",
        origin = origin ?: "",
        description = description ?: "",
        lifeSpan = lifeSpan ?: ""
    )
}

internal fun WeightResponse.toModel(): WeightModel {
    return WeightModel(
        imperial = imperial ?: "", metric = metric ?: ""
    )
}