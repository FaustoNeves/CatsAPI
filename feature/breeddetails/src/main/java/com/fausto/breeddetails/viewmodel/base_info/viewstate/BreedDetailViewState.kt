package com.fausto.breeddetails.viewmodel.base_info.viewstate

import com.fausto.model.BreedModel

internal sealed class BreedDetailViewState {
    object Loading : BreedDetailViewState()
    data class Success(val breed: BreedModel) : BreedDetailViewState()
    data class Error(val errorMessage: String) : BreedDetailViewState()
}
