package com.fausto.breeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fausto.breeds.viewmodel.BreedsViewModel
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.components.IndeterminateCircularIndicator

@Composable
fun BreedsRoute(
    breedsViewModel: BreedsViewModel = viewModel(), modifier: Modifier = Modifier
) {
    val breedsViewState by breedsViewModel.breedsViewState.observeAsState(BreedsViewState.Loading)
    BreedsScreen(breedsViewState = breedsViewState,
        onScreenInitialized = { breedsViewModel.interpret(BreedsInteract.ViewCreated) },
        onRefresh = { breedsViewModel.interpret(BreedsInteract.OnRefreshAction) },
        onError = { breedsViewModel.interpret(BreedsInteract.OnErrorAction) },
        onSearch = { queryId -> breedsViewModel.interpret(BreedsInteract.OnSearchBreedAction(queryId)) },
        onClick = { referenceImageId, queryId ->
            breedsViewModel.interpret(
                BreedsInteract.OnBreedClickAction(
                    referenceImageId, queryId
                )
            )
        })
}

@Composable
fun BreedsScreen(
    breedsViewState: BreedsViewState,
    onScreenInitialized: () -> Unit,
    onRefresh: () -> Unit,
    onError: () -> Unit,
    onSearch: (String) -> Unit,
    onClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (breedsViewState) {
        is BreedsViewState.Loading ->
            IndeterminateCircularIndicator()

        is BreedsViewState.Error -> {
            Text(text = "Error", modifier = modifier.fillMaxSize())
        }

        is BreedsViewState.Success -> {
            Text(text = "Success", modifier = modifier.fillMaxSize())
        }

        BreedsViewState.SaveReferenceImageIdSuccess -> {
            Text(text = "SaveReferenceImageIdSuccess", modifier = modifier.fillMaxSize())
        }
    }
    LaunchedEffect(Unit) {
        onScreenInitialized()
    }
}

