package com.fausto.breeddetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.designsystem.components.ErrorDialog
import com.fausto.designsystem.components.IndeterminateCircularIndicator
import com.fausto.model.BreedImageModel
import com.fausto.model.BreedModel

@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    breedDetailViewState: BreedDetailViewState,
    getBreedDetail: () -> Unit,
    backButtonAction: () -> Unit,
) {
    Column {
    Button(onClick = {
        backButtonAction.invoke()
    }) {
        Text("Voltar")
    }
    }
    when (breedDetailViewState) {
        is BreedDetailViewState.InitialState -> {
            getBreedDetail.invoke()
        }

        is BreedDetailViewState.Loading -> {
            IndeterminateCircularIndicator()
        }

        is BreedDetailViewState.Success -> {
            SuccessState(
                modifier = Modifier,
                breedModel = breedDetailViewState.breed,
                breedImages = breedDetailViewState.breedImages
            )
        }

        is BreedDetailViewState.Error -> {
            ErrorDialog(confirmButtonAction = {
                backButtonAction.invoke()
            }, errorMessage = breedDetailViewState.errorMessage)
        }
    }
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    breedModel: BreedModel,
    breedImages: List<BreedImageModel>
) {
    AsyncImage(modifier = Modifier, model = breedModel.url, contentDescription = "cat image")
}