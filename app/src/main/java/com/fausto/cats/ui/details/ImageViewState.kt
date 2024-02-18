package com.fausto.cats.ui.details

import com.fausto.cats.domain.model.BreedImageModel

internal sealed class ImageViewState {
    object Loading : ImageViewState()
    data class Success(val images: List<BreedImageModel>) : ImageViewState()
    data class Error(val errorMessage: String) : ImageViewState()
}