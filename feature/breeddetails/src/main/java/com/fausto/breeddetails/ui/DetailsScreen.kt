package com.fausto.breeddetails.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    breedId: String,
    imageQueryId: String,
    backButtonAction: () -> Unit
) {
    Button(onClick = {
        backButtonAction.invoke()
    }) {
        Text("Voltar")
    }
    Text("breedId: $breedId, imageQueryId: $imageQueryId")
}