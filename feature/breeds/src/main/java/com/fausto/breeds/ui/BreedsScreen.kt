package com.fausto.breeds.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.components.BreedCard
import com.fausto.designsystem.components.CatsSnackbar
import com.fausto.designsystem.components.ErrorDialog
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.designsystem.components.SearchTextField
import com.fausto.model.SectionModel

@Composable
internal fun BreedsScreen(
    modifier: Modifier = Modifier.fillMaxSize(), breedsViewState: BreedsViewState,
    onBreedClick: (breedId: String) -> Unit,
    onError: () -> Unit,
    onSearch: () -> Unit,
    userInput: String,
    updateUserInput: (String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SearchTextField(
            modifier = Modifier,
            onSearch = onSearch,
            userInput = userInput,
            updateUserInput = updateUserInput
        )
    when (breedsViewState) {
        is BreedsViewState.Loading -> LoadingState()

        is BreedsViewState.Error -> {
            ErrorState(
                modifier = Modifier,
                errorMessage = breedsViewState.errorMessage,
                retryAction = onError,
            )
        }

        is BreedsViewState.Success -> {
                SuccessState(
                    modifier = Modifier, breedsViewState.breedsList, onBreedClick
                )
        }

        is BreedsViewState.NoResultsFound -> {
            EmptyResultState(
                breedsViewState,
                snackbarHostState,
                com.fausto.texts.R.string.no_breeds_found
            )
        }
    }
        Box(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(
                modifier = Modifier.align(Alignment.BottomCenter),
                hostState = snackbarHostState
            ) {
                CatsSnackbar(
                    snackbarData = it, modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    IndeterminateCircularIndicator(modifier = modifier)
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier.fillMaxSize(),
    errorMessage: String,
    retryAction: () -> Unit,
) {
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
    onBreedClick: (breedId: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        sectionModelList.forEach { currentSection ->
            item {
                BreedCard(
                    modifier = modifier,
                    currentSection = currentSection.section ?: "",
                    breedsCollection = currentSection.breedsList,
                    onItemClick = onBreedClick
                )
            }
        }
    }
}

@Composable
private fun EmptyResultState(
    state: BreedsViewState, snackbarHostState: SnackbarHostState,
    @StringRes
    message: Int
) {
    val noFoundMessage = stringResource(message)
    if (state is BreedsViewState.NoResultsFound) {
        LaunchedEffect(state) {
            snackbarHostState.showSnackbar(
                message = noFoundMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}