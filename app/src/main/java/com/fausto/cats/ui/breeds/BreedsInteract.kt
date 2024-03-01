package com.fausto.cats.ui.breeds

internal sealed interface BreedsInteract {
    object ViewCreated : BreedsInteract
    object OnRefreshAction : BreedsInteract
    object OnErrorAction : BreedsInteract
    data class OnSearchBreedAction(val breedQuery: String) : BreedsInteract
}