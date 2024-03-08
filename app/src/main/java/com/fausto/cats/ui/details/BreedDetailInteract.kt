package com.fausto.cats.ui.details

internal sealed interface BreedDetailInteract {
    data class ViewCreated(val breedId: String) : BreedDetailInteract
    data class OnErrorAction(val breedId: String) : BreedDetailInteract
}