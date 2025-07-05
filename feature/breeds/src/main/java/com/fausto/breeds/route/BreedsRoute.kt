package com.fausto.breeds.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fausto.breeds.ui.BreedsScreen
import com.fausto.breeds.viewmodel.BreedsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object BreedsRoute

fun NavGraphBuilder.breedsScreen(
    modifier: Modifier, onBreedClick: (breedId: String, imageQueryId: String) -> Unit
) {
    composable<BreedsRoute> {
        BreedsRoute(modifier = modifier, onBreedClick = { breedId: String, imageQueryId: String ->
            onBreedClick(breedId, imageQueryId)
        })
    }
}

@Composable
fun BreedsRoute(
    modifier: Modifier = Modifier,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
    breedsViewModel: BreedsViewModel = hiltViewModel(),
) {
    val breedFlowViewState by breedsViewModel.breedFlowViewState.collectAsStateWithLifecycle()
    val breedsViewState by breedsViewModel.breedsViewState.observeAsState()
    breedsViewState?.let { screenState ->
        BreedsScreen(
            modifier = modifier,
            breedsViewState = screenState,
            onBreedClick = onBreedClick,
            onError = breedsViewModel::getBreeds,
            onSearch = breedsViewModel::getBreeds,
            userInput = breedsViewModel.userInput,
            updateUserInput = breedsViewModel::updateUserInput,
        )
    }
}