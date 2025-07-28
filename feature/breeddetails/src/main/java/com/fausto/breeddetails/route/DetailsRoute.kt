package com.fausto.breeddetails.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fausto.breeddetails.ui.DetailsScreen
import com.fausto.breeddetails.viewmodel.base_info.BreedDetailViewModel
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val id: String)

fun NavController.navigateToDetailsScreen(breedId: String) {
    navigate(route = DetailsRoute(id = breedId))
}

fun NavGraphBuilder.detailsScreen(modifier: Modifier, backButtonAction: () -> Unit) {
    composable<DetailsRoute> { backStackEntry ->
        val detailsRoute = backStackEntry.toRoute<DetailsRoute>()
        DetailsRoute(
            modifier = modifier,
            breedId = detailsRoute.id,
            backButtonAction = backButtonAction,
        )
    }
}

@Composable
fun DetailsRoute(
    modifier: Modifier,
    breedId: String,
    backButtonAction: () -> Unit,
    breedDetailsViewModel: BreedDetailViewModel = hiltViewModel()
) {
    val breedDetailViewState by breedDetailsViewModel.breedDetailViewState.observeAsState()
    breedDetailViewState?.let { screenState ->
        DetailsScreen(
            modifier = modifier,
            breedDetailViewState = screenState,
            getBreedDetail = { breedDetailsViewModel.getBreedDetail(breedId) },
            backButtonAction = backButtonAction
        )
    }
}