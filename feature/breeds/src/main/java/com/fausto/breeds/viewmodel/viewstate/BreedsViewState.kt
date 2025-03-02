package com.fausto.breeds.viewmodel.viewstate

import com.fausto.model.SectionModel

sealed class BreedsViewState {
    data object Loading : BreedsViewState()
    data class Success(val sections: List<SectionModel>) : BreedsViewState()
    data class Error(val errorMessage: String) : BreedsViewState()
}