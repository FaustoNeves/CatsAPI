package com.fausto.breeds.viewmodel.interact

internal sealed interface BreedsInteract {
    object ViewCreated : BreedsInteract
    object OnRefreshAction : BreedsInteract
    object OnErrorAction : BreedsInteract
    data class OnSearchBreedAction(val breedQuery: String) : BreedsInteract
    data class OnBreedClickAction(val breedQueryId: String) : BreedsInteract
}