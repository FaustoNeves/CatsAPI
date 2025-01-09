package com.fausto.breeds.viewmodel.interact

sealed interface BreedsInteract {
    object ViewCreated : BreedsInteract
    object OnRefreshAction : BreedsInteract
    object OnErrorAction : BreedsInteract
    data class OnSearchBreedAction(val breedQuery: String) : BreedsInteract
    data class OnBreedClickAction(val referenceImageId: String, val queryBreedId: String) :
        BreedsInteract
}