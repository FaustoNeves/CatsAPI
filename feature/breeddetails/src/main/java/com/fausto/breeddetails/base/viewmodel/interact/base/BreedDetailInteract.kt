package com.fausto.breeddetails.base.viewmodel.interact.base

internal sealed interface BreedDetailInteract {
    object ViewCreated : BreedDetailInteract
    data class HandleDeeplink(val breedQueryId: String) : BreedDetailInteract
    object OnErrorAction : BreedDetailInteract
}