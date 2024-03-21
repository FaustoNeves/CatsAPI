package com.fausto.breeds.adapter

import com.fausto.model.BreedsModel

internal sealed class BreedsBySearchViewState {
    object Loading : BreedsBySearchViewState()
    data class Success(val breedsModel: List<BreedsModel>) : BreedsBySearchViewState()
    data class Error(val errorMessage: String) : BreedsBySearchViewState()
}
