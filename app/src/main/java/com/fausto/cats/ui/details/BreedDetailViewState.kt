package com.fausto.cats.ui.details

import com.fausto.cats.domain.model.BreedModel

internal sealed class BreedDetailViewState {
    object Loading : BreedDetailViewState()
    data class Success(val breeds: BreedModel) : BreedDetailViewState()
    data class Error(val errorMessage: String) : BreedDetailViewState()
}
