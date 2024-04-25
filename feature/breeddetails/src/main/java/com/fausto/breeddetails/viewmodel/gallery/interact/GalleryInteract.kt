package com.fausto.breeddetails.viewmodel.gallery.interact

internal sealed interface GalleryInteract {

    object ViewCrated : GalleryInteract
    object OnErrorAction : GalleryInteract
}