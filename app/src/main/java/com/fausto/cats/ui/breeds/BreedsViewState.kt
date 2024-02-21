package com.fausto.cats.ui.breeds

import com.fausto.cats.domain.model.SectionModel

internal sealed class BreedsViewState {
    object Loading : BreedsViewState()
    data class Success(val breeds: List<SectionModel>) : BreedsViewState()
    data class Error(val errorMessage: String) : BreedsViewState()
}