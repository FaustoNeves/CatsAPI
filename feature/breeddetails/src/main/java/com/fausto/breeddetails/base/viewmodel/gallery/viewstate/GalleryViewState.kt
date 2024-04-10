package com.fausto.breeddetails.base.viewmodel.gallery.viewstate

import com.fausto.model.BreedImageModel

internal sealed class GalleryViewState {
    object Loading : GalleryViewState()
    data class Success(val imagesList: List<BreedImageModel>) : GalleryViewState()
    object Error : GalleryViewState()
}