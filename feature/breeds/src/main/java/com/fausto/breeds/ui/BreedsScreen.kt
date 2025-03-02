package com.fausto.breeds.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fausto.breeds.viewmodel.BreedsViewModel
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.designsystem.components.dialog.ErrorDialog
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
        onScreenInitialized = breedsViewModel::getBreeds,
        onError = breedsViewModel::getBreeds,
        onSearch = breedsViewModel::getBreedsBySearch,
        userInput = breedsViewModel.userInput,
        updateUserInput = breedsViewModel::updateUserInput,
    )
}

@Composable
private fun BreedsScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    breedsViewState: BreedsViewState,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
    onScreenInitialized: () -> Unit,
    onError: () -> Unit,
    onSearch: (String) -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit
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
            Column {
                BuildSearchTextField(
                    onSearch, userInput,
                    updateUserInput
                )
                SuccessState(
                    modifier = Modifier,
                    breedsViewState.sections,
                    onBreedClick,
//                onSearch
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        onScreenInitialized()
    }
}

@Composable
private fun ErrorState(modifier: Modifier, errorMessage: String, retryAction: () -> Unit) {
    ErrorDialog(
        modifier = modifier,
        confirmButtonAction = {
            retryAction.invoke()
        },
        errorMessage = errorMessage,
    )
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    sectionModelList: List<SectionModel>,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
//    onSearch: (String) -> Unit
) {
//        buildSearchTextField(onSearch)
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
                HorizontalDivider(modifier = Modifier.padding(bottom = 2.dp), thickness = 1.dp)
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
private fun BuildSearchTextField(
    onSearch: (String) -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userInput,
        onValueChange = {
            updateUserInput(it)
            onSearch.invoke(userInput)
        },
        label = { Text("Input breed") },
        placeholder = { Text("Persian...") },
        singleLine = true,
        maxLines = 1,
    )
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