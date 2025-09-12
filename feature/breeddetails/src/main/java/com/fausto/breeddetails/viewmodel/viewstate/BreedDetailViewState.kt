package com.fausto.breeddetails.viewmodel.viewstate

import com.fausto.model.BreedImageModel
import com.fausto.model.BreedModel

sealed class BreedDetailViewState {
    data object InitialState : BreedDetailViewState()
    data object Loading : BreedDetailViewState()
    data class Success(val breed: BreedModel, val breedImages: List<BreedImageModel>) :
        BreedDetailViewState()
    data class Error(val errorMessage: String) : BreedDetailViewState()
}
