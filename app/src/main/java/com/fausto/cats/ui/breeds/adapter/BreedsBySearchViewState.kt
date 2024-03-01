package com.fausto.cats.ui.breeds.adapter

import com.fausto.cats.domain.model.BreedsModel

internal sealed class BreedsBySearchViewState {
    object Loading : BreedsBySearchViewState()
    data class Success(val breedsModel: List<BreedsModel>) : BreedsBySearchViewState()
    data class Error(val errorMessage: String) : BreedsBySearchViewState()
}
