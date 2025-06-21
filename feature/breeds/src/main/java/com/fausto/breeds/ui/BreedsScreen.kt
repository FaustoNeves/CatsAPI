package com.fausto.breeds.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.components.BreedCard
import com.fausto.designsystem.components.ErrorDialog
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.designsystem.components.SearchTextField
import com.fausto.model.SectionModel

@Composable
internal fun BreedsScreen(
    modifier: Modifier = Modifier.fillMaxSize(), breedsViewState: BreedsViewState,
    onBreedClick: (breedId: String, imageQueryId: String) -> Unit,
    onError: () -> Unit,
    onSearch: () -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit
) {
    Column {
        SearchTextField(
            onSearch = onSearch,
            userInput = userInput,
            updateUserInput = updateUserInput
        )
    when (breedsViewState) {
        is BreedsViewState.Loading ->
            IndeterminateCircularIndicator()

        is BreedsViewState.Error -> {
            ErrorState(
                modifier = modifier,
                errorMessage = breedsViewState.errorMessage,
                retryAction = onError,
                errorId = breedsViewState.errorId
            )
        }

        is BreedsViewState.Success -> {
                SuccessState(
                    modifier = Modifier, breedsViewState.breedsList, onBreedClick
                )
        }
    }
    }
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier,
    errorMessage: String,
    retryAction: () -> Unit,
    errorId: String
) {
    ErrorDialog(
        modifier = modifier,
        errorId = errorId,
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
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        sectionModelList.forEach { currentSection ->
            item {
                BreedCard(
                    currentSection = currentSection.section ?: "",
                    breedsCollection = currentSection.breedsList,
                    onItemClick = onBreedClick
                )
            }
        }
    }
}