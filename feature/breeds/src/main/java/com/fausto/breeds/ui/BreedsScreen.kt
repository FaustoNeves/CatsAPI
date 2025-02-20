package com.fausto.breeds.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fausto.breeds.viewmodel.BreedsViewModel
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.components.ErrorScreen
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.model.BreedsModel
import com.fausto.model.SectionModel

@Composable
internal fun BreedsRoute(
    modifier: Modifier = Modifier,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
    breedsViewModel: BreedsViewModel = hiltViewModel()
) {
    val breedsViewState by breedsViewModel.breedsViewState.observeAsState(BreedsViewState.Loading)
    BreedsScreen(
        modifier = modifier,
        breedsViewState = breedsViewState,
        onBreedClick = onBreedClick,
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
private fun BreedsScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    breedsViewState: BreedsViewState,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
    onScreenInitialized: () -> Unit,
    onRefresh: () -> Unit,
    onError: () -> Unit,
    onSearch: (String) -> Unit,
    onClick: (String, String) -> Unit
) {
    when (breedsViewState) {
        is BreedsViewState.Loading ->
            IndeterminateCircularIndicator()

        is BreedsViewState.Error -> {
            ErrorState(
                modifier = modifier,
                errorMessage = breedsViewState.errorMessage,
                retryAction = onError
            )
        }

        is BreedsViewState.Success -> {
            SuccessState(modifier = Modifier.fillMaxSize(), breedsViewState.sections, onBreedClick)
        }

        BreedsViewState.SaveReferenceImageIdSuccess -> {
            Text(modifier = modifier.fillMaxSize(), text = "SaveReferenceImageIdSuccess")
        }
    }
    LaunchedEffect(Unit) {
        onScreenInitialized()
    }
}

@Composable
private fun ErrorState(modifier: Modifier, errorMessage: String, retryAction: () -> Unit) {
    ErrorScreen(modifier = modifier, errorMessage = errorMessage, retryAction = {
        retryAction.invoke()
    })
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    sectionModelList: List<SectionModel>,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        sectionModelList.forEach { currentSection ->
            item {
                Text(
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                    text = currentSection.section ?: ""
                )
                HorizontalDivider(thickness = 2.dp)
            }
            items(items = currentSection.breedsList) { currentBreed ->
                BreedItem(
                    modifier = modifier, breed = currentBreed
                ) { queryBreedId, referenceImageId ->
                    onBreedClick(queryBreedId, referenceImageId)
                }
            }
        }
    }
}

@Composable
private fun BreedItem(
    modifier: Modifier,
    breed: BreedsModel,
    onItemClick: (queryBreedId: String, referenceImageId: String) -> Unit
) {
    Text(modifier = modifier.clickable {
        onItemClick(breed.queryBreedId, breed.referenceImageId)
    }, text = breed.name)
}