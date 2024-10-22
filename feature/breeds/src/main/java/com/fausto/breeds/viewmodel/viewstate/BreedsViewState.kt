package com.fausto.breeds.viewmodel.viewstate

import com.fausto.model.SectionModel

internal sealed class BreedsViewState {
    object Loading : BreedsViewState()
    data class Success(val sections: List<SectionModel>) : BreedsViewState()
    data class Error(val errorMessage: String) : BreedsViewState()
    object SaveReferenceImageIdSuccess : BreedsViewState()
}