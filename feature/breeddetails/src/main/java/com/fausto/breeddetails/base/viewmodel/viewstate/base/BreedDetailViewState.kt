package com.fausto.breeddetails.base.viewmodel.viewstate.base

import com.fausto.model.BreedModel

internal sealed class BreedDetailViewState {
    object Loading : BreedDetailViewState()
    data class Success(val breed: BreedModel) : BreedDetailViewState()
    data class Error(val errorMessage: String) : BreedDetailViewState()
}
