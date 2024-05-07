package com.fausto.breeddetails.viewmodel.gallery.viewstate

import com.fausto.model.BreedImageModel

internal sealed class GalleryViewState {
    object Loading : GalleryViewState()
    data class Success(val imagesList: List<BreedImageModel>) : GalleryViewState()
    data class Error(val errorMessage: String? = null) : GalleryViewState()
}