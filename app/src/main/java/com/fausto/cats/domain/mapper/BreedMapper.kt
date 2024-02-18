package com.fausto.cats.domain.mapper

import com.fausto.cats.data.response.BreedDetailResponse
import com.fausto.cats.data.response.BreedResponse
import com.fausto.cats.data.response.WeightResponse
import com.fausto.cats.domain.model.BreedDetailModel
import com.fausto.cats.domain.model.BreedModel
import com.fausto.cats.domain.model.WeightModel

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