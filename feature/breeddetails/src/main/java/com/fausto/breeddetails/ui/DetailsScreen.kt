package com.fausto.breeddetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun DetailsRoute(modifier: Modifier, breedId: String, imageQueryId: String) {
    DetailsScreen(modifier, breedId, imageQueryId)
}

@Composable
private fun DetailsScreen(modifier: Modifier = Modifier, breedId: String, imageQueryId: String) {
    Text("breedId: $breedId, imageQueryId: $imageQueryId")
}