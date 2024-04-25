package com.fausto.breeddetails.viewmodel.base_info.interact

internal sealed interface BreedDetailInteract {
    object BaseViewCreated : BreedDetailInteract
    data class BaseHandleDeeplink(val breedQueryId: String) : BreedDetailInteract
    object BaseOnErrorAction : BreedDetailInteract
}