package com.fausto.breeddetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.designsystem.components.IndeterminateCircularIndicator

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
            Text("Início")
//            getBreedDetail.invoke()
        }

        is BreedDetailViewState.Loading -> {
            IndeterminateCircularIndicator()
        }

        is BreedDetailViewState.Success -> {
            Text("Sucesso carai")
        }

        is BreedDetailViewState.Error -> {
//            ErrorDialog()
        }
    }
}