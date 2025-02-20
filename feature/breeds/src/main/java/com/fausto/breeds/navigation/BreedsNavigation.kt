package com.fausto.breeds.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fausto.breeds.ui.BreedsRoute
import kotlinx.serialization.Serializable

@Serializable
data object BreedsRoute

fun NavGraphBuilder.breedsScreen(onBreedClick: (breedId: String, imageQueryId: String) -> Unit) {
    composable<BreedsRoute> {
        BreedsRoute(onBreedClick = { breedId: String, imageQueryId: String ->
            onBreedClick(breedId, imageQueryId)
        })
    }
}