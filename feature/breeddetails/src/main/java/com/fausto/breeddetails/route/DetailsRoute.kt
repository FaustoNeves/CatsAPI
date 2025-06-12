package com.fausto.breeddetails.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fausto.breeddetails.ui.DetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val id: String, val imageQueryId: String)

fun NavController.navigateToDetailsScreen(breedId: String, imageQueryId: String) {
    navigate(route = DetailsRoute(id = breedId, imageQueryId = imageQueryId))
}

fun NavGraphBuilder.detailsScreen(modifier: Modifier, backButtonAction: () -> Unit) {
    composable<DetailsRoute> { backStackEntry ->
        val detailsRoute = backStackEntry.toRoute<DetailsRoute>()
        DetailsRoute(
            modifier = modifier,
            breedId = detailsRoute.id,
            imageQueryId = detailsRoute.imageQueryId,
            backButtonAction = backButtonAction,
        )
    }
}

@Composable
fun DetailsRoute(
    modifier: Modifier,
    breedId: String,
    imageQueryId: String,
    backButtonAction: () -> Unit
) {
    DetailsScreen(modifier, breedId, imageQueryId, backButtonAction)
}