package com.fausto.cats.ui.breeds

import com.fausto.cats.domain.model.BreedsModel

internal sealed class BreedsViewState {
    object Loading : BreedsViewState()
    data class Success(val breeds: List<BreedsModel>) : BreedsViewState()
    data class Error(val errorMessage: String) : BreedsViewState()
}